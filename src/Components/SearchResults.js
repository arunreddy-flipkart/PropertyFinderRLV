import React from 'react';
import {
  Dimensions,
  StyleSheet,
  View,
  TextInput,
  Button,
  ActivityIndicator,
  Text
} from 'react-native';
import {RecyclerListView, LayoutProvider, DataProvider, BaseItemAnimator, BaseLayoutProvider } from 'recyclerlistview';
import ListItem from './ListItem';
import getApi from '../API/PropertyAPI';
const {height, width} = Dimensions.get('window');

class SearchResults extends React.Component {
  
  constructor(props) {
    super(props);
    let data = this.props.navigation.getParam('listings', []);
    let dataProvider = new DataProvider((r1,r2) => {
      return r1 !== r2
    });
    this.layoutProvider = new LayoutProvider((i) => {
      return i.toString();
    }, (type,dim) => {
      switch(type) {
        default:
          dim.width = width;
          dim.height = 50;
      }
    });
    this.itemAnimator = new BaseItemAnimator();
    this.recyclerListViewRef = React.createRef();
    this.state = {
      searchString: this.props.navigation.getParam('searchString', ''),
      isLoading: false,
      message: '',
      dataProvider: dataProvider.cloneWithRows(data),
    }
  }

  textChanged = (event) => {
    this.setState({ 
      searchString: event.nativeEvent.text 
    });
  }

  handleResponse  = (response) => {
    if (response.application_response_code.substr(0, 1) === '1') {
      listings = response.listings;
      console.log('Properties found: ' + listings.length);
      while(listings.length<=100) {
        listings = listings.concat(listings);
      }
      this.setState({
        isLoading: false,
        message: '',
        dataProvider: this.state.dataProvider.cloneWithRows(listings)
      })
    } else {
      this.setState({ 
        isLoading: false, 
        message: 'Location not recognized; please try again.'
      });
    }
  }

  makeApiCall = (query) => {
    this.setState({
      isLoading: true
    });
    fetch(query)
      .then(response => response.json())
      .then(json => this.handleResponse(json.response))
      .catch(error =>
        this.setState({
          isLoading: false,
          message: 'Something bad happened ' + error
        })
      );
  }

  searchPressed = () => {
    const query = getApi('place_name', this.state.searchString, 1);
    this.makeApiCall(query);
  }

  onPressItem = (index) => {
    console.log("Pressed row: "+index);
  };

  renderItem = (type, item, index) => {
      return (
        <ListItem
          item={item}
          index={index}
          onPressItem={this.onPressItem}
        />
      );
  };

  renderFooter = () => {
    return (
      <Text>Rendering Footer</Text>
    )
  }

  render() {
    let renderFooter = this.state.dataProvider.getSize()===0 ? this.renderFooter : null;
    return (
      <View style={styles.rootContainer}>
        <View style={styles.flowRight}>
          <TextInput
              style={styles.searchInput}
              value={this.state.searchString}
              onChange={this.textChanged}
              onSubmitEditing={this.searchPressed}
              placeholder='Search via name or postcode'/>
            <Button
              onPress={this.searchPressed}
              color='#48BBEC'
              title='Go'
            />
        </View>
        {this.state.isLoading ? <ActivityIndicator size='large'/> :
          <View style={styles.listContainer}>
            <RecyclerListView
              ref={this.recyclerListViewRef}
              rowRenderer={this.renderItem}
              dataProvider={this.state.dataProvider}
              layoutProvider={this.layoutProvider}
              itemAnimator={this.itemAnimator}
              forceNonDeterministicRendering={true}
              renderFooter={renderFooter}
            />
        </View>
        }
      </View>
    );
  };
}

const styles = StyleSheet.create({
  flowRight: {
    flexDirection: 'row',
    alignItems: 'center',
    alignSelf: 'stretch',
    padding: 8,
  },
  searchInput: {
    height: 36,
    padding: 4,
    marginRight: 5,
    flexGrow: 1,
    fontSize: 20,
    borderWidth: 1,
    borderColor: '#48BBEC',
    borderRadius: 8,
    color: '#48BBEC',
  },
  listContainer: {
    flex: 1
  },
  rootContainer: {
    flex: 1
  }
});
export default SearchResults;

import React from 'react';
import {
  StyleSheet,
  View,
  Text,
  TextInput,
  Button,
  Image,
  ActivityIndicator
} from 'react-native';
import getApi from '../API/PropertyAPI';

class SearchPage extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      searchString: 'london',
      isLoading: false,
      message: '',
    }
  }

  textChanged = (event) => {
    this.setState({ 
      searchString: event.nativeEvent.text 
    });
  }

  handleResponse  = (response) => {
    this.setState({ 
      isLoading: false,
      message: '' 
    });
    if (response.application_response_code.substr(0, 1) === '1') {
      listings = response.listings;
      console.log('Properties found: ' + listings.length);
      while(listings.length<=1000) {
        let count = listings.length;
        for(i=0;i<count;i++) {
          listings.push(JSON.parse(JSON.stringify(listings[i])));
        }
      }
      this.props.navigation.navigate('Results', {
        listings: listings,
        searchString: this.state.searchString
      });
    } else {
      this.setState({ 
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

  render() { 
    return (
      <View style={styles.container}>
        <Text style={styles.description}>
          Search for houses to buy!
        </Text>
        <Text style={styles.description}>
          Search by place-name or postcode.
        </Text>
        <View style={styles.flowRight}>
          <TextInput
            style={styles.searchInput}
            value={this.state.searchString}
            onChange={this.textChanged}
            placeholder='Search via name or postcode'/>
          <Button
            onPress={this.searchPressed}
            color='#48BBEC'
            title='Go'
          />
        </View>
        <Image source={require('../../resources/house.png')} style={styles.image}/>
        {this.state.isLoading ? <ActivityIndicator size='large'/> : null}
        <Text style={styles.description}>{this.state.message}</Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  description: {
    fontSize: 18,
    textAlign: 'center',
    color: '#656565',
    marginTop: 10,
  },
  container: {
    padding: 30,
    marginTop: 65,
    alignItems: 'center'
  },
  flowRight: {
    flexDirection: 'row',
    alignItems: 'center',
    alignSelf: 'stretch',
    marginTop: 20
  },
  searchInput: {
    height: 36,
    padding: 4,
    marginRight: 5,
    flexGrow: 1,
    fontSize: 18,
    borderWidth: 1,
    borderColor: '#48BBEC',
    borderRadius: 8,
    color: '#48BBEC',
  },
  image: {
    width: 217,
    height: 138,
  },
});

export default SearchPage;
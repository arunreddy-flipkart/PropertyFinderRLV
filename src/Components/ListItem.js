import React from 'react';
import {
  Dimensions,
  StyleSheet,
  Image,
  View,
  TouchableHighlight,
  Text
} from 'react-native';

const width = Dimensions.get('window').width;

class ListItem extends React.PureComponent {
    
  onPress = () => {
    this.props.onPressItem(this.props.index);
  }

  render() {
    const item = this.props.item;
    let title = item.title;
    let e = Math.random();
    //console.log('Rendered index '+this.props.index);
    if(e<0.3) {
      title = title.concat(title.concat(title));
    } else if(e>0.7) {
      title = title.concat(title.concat(title.concat(title)));
    }
    const price = item.price_formatted.split(' ')[0];
    return (
      /*<Text style={styles.price}>{'Welcome to Index '+this.props.index}</Text>*/
      <TouchableHighlight
        onPress={this.onPress}
        underlayColor='#dddddd'>
        <View>
          <View style={styles.rowContainer}>
            <View style={styles.imageContainer}>
              <Image style={styles.thumb} source={{ uri: item.img_url }} />
            </View>
            <View style={styles.textContainer}>
              <Text style={styles.price}>{price}</Text>
              <View style={styles.titleContainer}>
                <Text style={styles.title}
                   >{title}</Text>
              </View>
            </View>
          </View>
          <View style={styles.separator}/>
        </View>
      </TouchableHighlight>
    );
  }
}

const styles = StyleSheet.create({
  thumb: {
    width: 80,
    height: 80,
    marginRight: 10
  },
  imageContainer: {
    flex: 1
  },
  separator: {
    height: 1,
    width: width,
    backgroundColor: '#dddddd'
  },
  price: {
    fontSize: 25,
    fontWeight: 'bold',
    color: '#48BBEC'
  },
  title: {
    fontSize: 20,
    color: '#656565',
    flex: 1,
    flexWrap: 'wrap'
  },
  titleContainer: {
    flex: 1,
  },
  textContainer: {
    flex: 3,
    flexWrap: 'wrap',
  },
  rowContainer: {
    flexDirection: 'row',
    flex: 1,
    padding: 10
  },
});

export default ListItem;
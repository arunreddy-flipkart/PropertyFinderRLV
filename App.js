import {createAppContainer} from 'react-navigation';
import {createStackNavigator} from 'react-navigation-stack';
import React from 'react';
import SearchPage from './src/Components/SearchPage';
import SearchResults from './src/Components/SearchResults';

const MainNavigator = createStackNavigator(
  {
    Home: {screen: SearchPage},
    Results: {screen: SearchResults},
  },
  {
    initialRouteName: 'Home',
  }
);
const AppContainer = createAppContainer(MainNavigator);

class App extends React.Component {
  render() {
    return <AppContainer/>
  }
}

export default App;

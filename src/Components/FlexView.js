import PropTypes from 'prop-types';
import { requireNativeComponent, ViewPropTypes} from 'react-native';

const viewProps = {
  name: 'FlexView',
  propTypes: {
    isHorizontal: PropTypes.bool,
    ...ViewPropTypes,
  }
}

const FlexView = requireNativeComponent('FlexView', viewProps);
export default FlexView;

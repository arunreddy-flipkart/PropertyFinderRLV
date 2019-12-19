package com.propertyfinder1;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

public class FlexViewManager extends ViewGroupManager {

    public static final String REACT_CLASS = "FlexView";
    public static final String PROP_HORIZONTAL = "horizontal";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected FlexView createViewInstance(ThemedReactContext reactContext) {
        return new FlexView(reactContext);
    }

    @ReactProp(name=PROP_HORIZONTAL, defaultBoolean = false)
    public void setHorizontal(FlexView flexView, boolean isHorizontal) {
        flexView.setHorizontal(isHorizontal);
    }
}
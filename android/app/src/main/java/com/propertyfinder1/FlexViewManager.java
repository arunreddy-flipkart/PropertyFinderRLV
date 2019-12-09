package com.propertyfinder1;

import android.view.View;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

public class FlexViewManager extends ViewGroupManager {

    public static final String REACT_CLASS = "FlexView";
    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected View createViewInstance(@NonNull ThemedReactContext reactContext) {
        FlexView flexView = new FlexView(reactContext);
        return flexView;
    }

    @ReactProp(name="isHorizontal",defaultBoolean = false)
    public void setFlexDirection(FlexView flexView, boolean isHorizontal) {
        flexView.setFlexDirection(isHorizontal);
    }

}

package com.propertyfinder1;

import com.facebook.react.views.view.ReactViewGroup;
import com.propertyfinder1.util.ViewComparator;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;

public class FlexView extends ReactViewGroup {

    protected boolean horizontal;
    private ArrayList<View> subviews;
    private ViewComparator comparator;

    public FlexView(Context context) {
        super(context);
        this.horizontal = false;
        this.subviews = new ArrayList<>();
        this.comparator = new ViewComparator();
        this.setWillNotDraw(false);
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
        this.comparator.setHorizontal(horizontal);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int count = getChildCount();
        FlexViewLayouter flexViewLayouter = new FlexViewLayouter(horizontal);
        for (int i = 0; i < count; i++) {
            subviews.add(getChildAt(i));
        }
        Collections.sort(subviews, comparator);

        for (int i = 0; i < count; i++) {
            final View child = subviews.get(i);
            flexViewLayouter.layout(child);
        }
        super.onDraw(canvas);
        subviews.clear();
    }
}
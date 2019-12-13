package com.propertyfinder1;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.facebook.react.views.view.ReactViewGroup;
import com.propertyfinder1.util.ViewComparator;

public class FlexView extends ReactViewGroup {

    protected boolean isHorizontal;

    public FlexView(Context context) {
        super(context);
        this.isHorizontal = false;
        this.setWillNotDraw(false);
    }

    public void setFlexDirection(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("MEASURE", "on Measure is called");
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        super.onLayout(b, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int count = getChildCount();
        FlexViewLayouter flexViewLayouter = new FlexViewLayouter(isHorizontal);
        ArrayList<View> views = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            views.add(child);
        }
        Collections.sort(views, new ViewComparator(isHorizontal));

        for (int i = 0; i < count; i++) {
            final View child = views.get(i);
            //Log.d("MEASURE", "Item "+i+" Layout: Original Top: "+child.getTop()+" Height: "+child.getHeight());
            flexViewLayouter.layout(child);
            //Log.d("MEASURE", "Item "+i+" Layout: Corrected Top: "+child.getTop()+" Height: "+child.getHeight());
        }
        //Log.d("MEASURE", "END");
        super.onDraw(canvas);
    }
}


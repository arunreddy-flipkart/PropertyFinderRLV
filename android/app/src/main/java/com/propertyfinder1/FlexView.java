package com.propertyfinder1;

import android.content.Context;
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
    }

    public void setFlexDirection(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Log.d("MEASURE", "Width: "+MeasureSpec.toString(widthMeasureSpec));
        //Log.d("MEASURE", "Height: "+MeasureSpec.toString(heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        super.onLayout(b, left, top, right, bottom);
        Log.d("MEASURE","Layout of FlexView Width: "+getWidth()+" Height: "+getHeight()+" Top: "+getTop());
        int count = getChildCount();
        FlexViewLayouter flexViewLayouter = new FlexViewLayouter();
        ArrayList<View> views = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            views.add(child);
        }
        Collections.sort(views, new ViewComparator(isHorizontal));

        for (int i = 0; i < count; i++) {
            final View child = views.get(i);
            if (child.getVisibility() != GONE) {
                if (isHorizontal) {
                    int estimatedPosition = child.getLeft();
                    int width = child.getWidth();
                    //Log.d("MEASURE", "Layout: Width: "+child.getWidth()+" Original Left: "+estimatedPosition);
                    int correctedPosition = flexViewLayouter.layout(child.getTop(), child.getBottom(), estimatedPosition, width);
                    child.layout(correctedPosition, child.getTop(), correctedPosition + width, child.getBottom());
                    //Log.d("MEASURE", "Layout: Width: "+child.getWidth()+" Corrected Left: "+correctedPosition);
                } else {
                    int estimatedPosition = child.getTop();
                    int height = child.getHeight();
                    //Log.d("MEASURE", "Index: "+i+" Layout: Height: "+child.getHeight()+" Original Top: "+estimatedPosition);
                    int correctedPosition = flexViewLayouter.layout(child.getLeft(), child.getRight(), estimatedPosition, height);
                    child.layout(child.getLeft(), correctedPosition, child.getRight(), correctedPosition + height);
                    //Log.d("MEASURE", "Index: "+i+" Layout: Height: "+child.getHeight()+" Corrected Top: "+correctedPosition);
                }
            }
        }
    }
}


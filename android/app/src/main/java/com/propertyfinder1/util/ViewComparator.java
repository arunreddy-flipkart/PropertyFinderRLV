package com.propertyfinder1.util;

import android.view.View;
import java.util.Comparator;

public class ViewComparator implements Comparator<View> {
    private boolean isHorizontal;
    public ViewComparator() {
        this.isHorizontal = false;
    }

    public ViewComparator(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    @Override
    public int compare(View a, View b) {
        if (isHorizontal) {
            return a.getLeft() - b.getLeft();
        } else {
            return a.getTop() - b.getTop();
        }
    }
}

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
            if (a.getLeft() == b.getLeft()) {
                return a.getRight() - b.getRight();
            }
            return a.getLeft() - b.getLeft();
        } else {
            if (a.getTop() == b.getTop()) {
                return a.getBottom() - b.getBottom();
            }
            return a.getTop() - b.getTop();
        }
    }
}

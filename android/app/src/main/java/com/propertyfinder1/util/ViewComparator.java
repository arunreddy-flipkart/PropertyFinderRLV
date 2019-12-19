package com.propertyfinder1.util;

import android.view.View;
import java.util.Comparator;

public class ViewComparator implements Comparator<View> {
    private boolean horizontal;
    public ViewComparator() {
        this.horizontal = false;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    @Override
    public int compare(View a, View b) {
        if (horizontal) {
            if (a.getLeft() == b.getLeft()) {
                return a.getTop() - b.getTop();
            }
            return a.getLeft() - b.getLeft();
        } else {
            if (a.getTop() == b.getTop()) {
                return a.getLeft() - b.getLeft();
            }
            return a.getTop() - b.getTop();
        }
    }
}

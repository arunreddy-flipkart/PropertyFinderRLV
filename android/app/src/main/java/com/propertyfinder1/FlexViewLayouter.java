package com.propertyfinder1;

import com.propertyfinder1.util.IntervalTree;
import com.propertyfinder1.util.Interval;

import android.view.View;
import java.util.HashMap;
import java.util.ArrayList;

public class FlexViewLayouter {
    private boolean horizontal;
    private IntervalTree intervalTree;
    private HashMap<String, Interval> intervalPositions;

    public FlexViewLayouter(boolean horizontal) {
        this.horizontal = horizontal;
        this.intervalTree = new IntervalTree();
        this.intervalPositions = new HashMap<>();
    }

    /*
    The algorithm which finds the overlaps and shifts the views accordingly. A Binary search tree is used to store the boundary of the views
    which have layouted so far. First, the left / x position of the view is corrected if overlaps are found such that xDiff > yDiff. Once the
    left position is corrected, new overlaps are checked for the updated position. If overlaps are found in the y-direction, then the view is
    shifted by an amount equal to the largest overlap. The views which go outside the bounds of the container will be clipped as the parent
    scrollview enforces the container view to have the size as specified by the user.
    */
    public void layout(View view) {
        int height = view.getHeight();
        int width = view.getWidth();
        int top = view.getTop();
        int left = view.getLeft();
        Interval currentInterval = horizontal ? new Interval(top, top + height) : new Interval(left, left + width);

        int correctedTop = top;
        int correctedLeft = left;
        ArrayList<Interval> overlappingIntervals = intervalTree.searchAll(currentInterval.getStart(), currentInterval.getEnd());
        for (int i = 0; i < overlappingIntervals.size(); i++) {
            Interval overlappingInterval = overlappingIntervals.get(i);
            Interval overlappingIntervalPosition = intervalPositions.get(overlappingInterval.toString());
            if (overlappingIntervalPosition == null) {
                continue;
            }
            if (horizontal) {
                int xDiff = Math.abs(correctedLeft - overlappingIntervalPosition.getStart());
                int yDiff = Math.abs(correctedTop - overlappingInterval.getStart());
                if (yDiff > xDiff){
                    correctedTop = Math.max(correctedTop, overlappingInterval.getEnd());
                }
            } else {
                int xDiff = Math.abs(correctedLeft - overlappingInterval.getStart());
                int yDiff = Math.abs(correctedTop - overlappingIntervalPosition.getStart());
                if (xDiff > yDiff) {
                    correctedLeft = Math.max(correctedLeft, overlappingInterval.getEnd());
                }
            }
        }
        if (horizontal) {
            currentInterval.setStart(correctedTop);
            currentInterval.setEnd(correctedTop + height);
        } else {
            currentInterval.setStart(correctedLeft);
            currentInterval.setEnd(correctedLeft + width);
        }

        overlappingIntervals = intervalTree.searchAll(currentInterval.getStart(), currentInterval.getEnd());
        for (int i = 0; i < overlappingIntervals.size(); i++) {
            Interval overlappingInterval = overlappingIntervals.get(i);
            Interval overlappingIntervalPosition = intervalPositions.get(overlappingInterval.toString());
            if (overlappingIntervalPosition == null) {
                continue;
            }
            if (horizontal) {
                correctedLeft = Math.max(correctedLeft, overlappingIntervalPosition.getEnd());
            } else {
                correctedTop = Math.max(correctedTop, overlappingIntervalPosition.getEnd());
            }

            if (currentInterval.equals(overlappingInterval)) {
                if (horizontal) {
                    updateInterval(currentInterval, new Interval(correctedLeft, correctedLeft + width));
                } else {
                    updateInterval(currentInterval, new Interval(correctedTop, correctedTop + height));
                }
                view.layout(correctedLeft, correctedTop, correctedLeft + width, correctedTop + height);
                return;
            }
            if (currentInterval.contains(overlappingInterval)) {
                removeInterval(overlappingInterval);
            } else if (overlappingInterval.contains(currentInterval)) {
                removeInterval(overlappingInterval);
                addInterval(new Interval(overlappingInterval.getStart(),currentInterval.getStart()), overlappingIntervalPosition);
                addInterval(new Interval(currentInterval.getEnd(),overlappingInterval.getEnd()), overlappingIntervalPosition);
            } else if (overlappingInterval.compareTo(currentInterval) < 0){
                removeInterval(overlappingInterval);
                addInterval(new Interval(overlappingInterval.getStart(),currentInterval.getStart()), overlappingIntervalPosition);
            } else {
                removeInterval(overlappingInterval);
                addInterval(new Interval(currentInterval.getEnd(), overlappingInterval.getEnd()), overlappingIntervalPosition);
            }
        }
        if (horizontal) {
            addInterval(currentInterval, new Interval(correctedLeft, correctedLeft + width));
        } else {
            addInterval(currentInterval, new Interval(correctedTop, correctedTop + height));
        }
        view.layout(correctedLeft, correctedTop, correctedLeft + width, correctedTop + height);
    }

    private void addInterval(Interval interval, Interval intervalPosition) {
        if(interval.getStart() == interval.getEnd()) {
            return;
        }
        intervalTree.add(interval);
        intervalPositions.put(interval.toString(), intervalPosition);
    }

    private void removeInterval(Interval interval) {
        intervalTree.remove(interval);
        intervalPositions.remove(interval.toString());
    }

    private void updateInterval(Interval interval, Interval intervalPosition) {
        intervalPositions.put(interval.toString(), intervalPosition);
    }

}

package com.propertyfinder1;

import android.util.Log;

import com.propertyfinder1.util.IntervalTree;
import com.propertyfinder1.util.Interval;

import java.util.HashMap;
import java.util.ArrayList;

public class FlexViewLayouter {
    private IntervalTree intervalTree;
    private HashMap<String, Integer> intervalPositions;

    public FlexViewLayouter() {
        intervalTree = new IntervalTree();
        intervalPositions = new HashMap<>();
    }

    public int layout(int start, int end, int estimatedPosition, int size) {

        Interval currentInterval = new Interval(start, end);
        int correctedPosition = estimatedPosition;
        ArrayList<Interval> overlappingIntervals = intervalTree.searchAll(start, end);

        Log.d("MEASURE", "Overlapping Intervals: "+overlappingIntervals.toString());
        //Log.d("MEASURE", "Interval Positions: "+intervalPositions.toString());
        for (int i = 0; i < overlappingIntervals.size(); i++) {
            Interval overlappingInterval = overlappingIntervals.get(i);
            int intervalPosition = intervalPositions.get(overlappingInterval.toString());
            correctedPosition = Math.max(correctedPosition, intervalPosition);

            if (currentInterval.equals(overlappingInterval)) {
                updateInterval(currentInterval, correctedPosition+size);
                return correctedPosition;
            }
            if (currentInterval.contains(overlappingInterval)) {
                removeInterval(overlappingInterval);
            } else if (overlappingInterval.contains(currentInterval)) {
                removeInterval(overlappingInterval);
                addInterval(new Interval(overlappingInterval.getStart(),currentInterval.getStart()), intervalPosition);
                addInterval(new Interval(currentInterval.getEnd(),overlappingInterval.getEnd()), intervalPosition);
            } else if (overlappingInterval.compareTo(currentInterval) < 0){
                removeInterval(overlappingInterval);
                addInterval(new Interval(overlappingInterval.getStart(),currentInterval.getStart()), intervalPosition);
            } else {
                removeInterval(overlappingInterval);
                addInterval(new Interval(currentInterval.getEnd(), overlappingInterval.getEnd()), intervalPosition);
            }
        }
        addInterval(currentInterval, correctedPosition+size);
        return correctedPosition;
    }

    private void addInterval(Interval interval, int intervalPosition) {
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

    private void updateInterval(Interval interval, int intervalPosition) {
        intervalPositions.put(interval.toString(), intervalPosition);
    }

}

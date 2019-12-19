//
//  FlexViewLayouter.swift
//  PropertyFinder1
//
//  Created by K Arun Kumar Reddy on 19/12/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation

class FlexViewLayouter {
    private var isHorizontal: Bool;
    private var intervalTree: IntervalTree;
    private var intervalPositions: [String: Interval];
    
    init(isHorizontal: Bool) {
        self.isHorizontal = isHorizontal;
        self.intervalTree = IntervalTree();
        self.intervalPositions = [String: Interval]();
    }
    
    func layout(view: UIView) {
        let height: Int = Int(view.frame.height);
        let width: Int = Int(view.frame.width);
        let left: Int = Int(view.frame.minX);
        let top: Int = Int(view.frame.minY);
        let currentInterval = isHorizontal ? Interval(start: top, end: top + height) : Interval(start: left, end: left + width);
        
        var correctedTop: Int = top;
        var correctedLeft: Int = left;
        var overlappingIntervals: Array<Interval> = intervalTree.searchAll(start: currentInterval.getStart(), end: currentInterval.getEnd());
        for overlappingInterval in overlappingIntervals {
            if let overlappingIntervalPosition: Interval = intervalPositions[overlappingInterval.description] {
                if (isHorizontal) {
                    let xDiff: Int = abs(correctedLeft - overlappingIntervalPosition.getStart());
                    let yDiff: Int = abs(correctedTop - overlappingInterval.getStart());
                    if (yDiff > xDiff){
                        correctedTop = max(correctedTop, overlappingInterval.getEnd());
                    }
                } else {
                    let xDiff: Int = abs(correctedLeft - overlappingInterval.getStart());
                    let yDiff: Int = abs(correctedTop - overlappingIntervalPosition.getStart());
                    if (xDiff > yDiff) {
                        correctedLeft = max(correctedLeft, overlappingInterval.getEnd());
                    }
                }
                
            }
        }
        if (isHorizontal) {
            currentInterval.setStart(start: correctedTop);
            currentInterval.setEnd(end: correctedTop + height);
        } else {
            currentInterval.setStart(start: correctedLeft);
            currentInterval.setEnd(end: correctedLeft + width);
        }
        overlappingIntervals = intervalTree.searchAll(start: currentInterval.getStart(), end: currentInterval.getEnd());
        for overlappingInterval in overlappingIntervals {
            if let overlappingIntervalPosition: Interval = intervalPositions[overlappingInterval.description] {
                if (isHorizontal) {
                    correctedLeft = max(correctedLeft, overlappingIntervalPosition.getEnd());
                } else {
                    correctedTop = max(correctedTop, overlappingIntervalPosition.getEnd());
                }
                
                if (currentInterval == overlappingInterval) {
                    if (isHorizontal) {
                        updateInterval(interval: currentInterval, intervalPosition: Interval(start: correctedLeft, end: correctedLeft + width));
                    } else {
                        updateInterval(interval: currentInterval, intervalPosition: Interval(start: correctedTop, end: correctedTop + height));
                    }
                    view.frame = CGRect(x: correctedLeft, y:correctedTop, width: width, height: height);
                    return;
                }
                if (currentInterval.contains(interval: overlappingInterval)) {
                    removeInterval(interval: overlappingInterval);
                } else if (overlappingInterval.contains(interval: currentInterval)) {
                    removeInterval(interval: overlappingInterval);
                    addInterval(interval: Interval(start: overlappingInterval.getStart(), end: currentInterval.getStart()), intervalPosition: overlappingIntervalPosition);
                    addInterval(interval: Interval(start: currentInterval.getEnd(),end: overlappingInterval.getEnd()), intervalPosition: overlappingIntervalPosition);
                } else if (overlappingInterval.compareTo(interval: currentInterval) < 0) {
                    removeInterval(interval: overlappingInterval);
                    addInterval(interval: Interval(start: overlappingInterval.getStart(), end: currentInterval.getStart()), intervalPosition: overlappingIntervalPosition);
                } else {
                    removeInterval(interval: overlappingInterval);
                    addInterval(interval: Interval(start: currentInterval.getEnd(), end: overlappingInterval.getEnd()), intervalPosition: overlappingIntervalPosition);
                }
            }
        }
        if (isHorizontal) {
            addInterval(interval: currentInterval, intervalPosition: Interval(start: correctedLeft, end: correctedLeft + width));
        } else {
            addInterval(interval: currentInterval, intervalPosition: Interval(start: correctedTop, end: correctedTop + height));
        }
        view.frame = CGRect(x: correctedLeft, y:correctedTop, width: width, height: height);
    }
    
    private func addInterval(interval: Interval, intervalPosition: Interval) {
        if(interval.getStart() == interval.getEnd()) {
            return;
        }
        intervalTree.add(interval: interval);
        intervalPositions[interval.description] = intervalPosition;
    }
    
    private func removeInterval(interval: Interval) {
        intervalTree.add(interval: interval);
        intervalPositions.removeValue(forKey: interval.description);
    }
    
    private func updateInterval(interval: Interval, intervalPosition: Interval) {
        intervalPositions[interval.description] = intervalPosition;
    }
}

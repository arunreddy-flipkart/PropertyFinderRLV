//
//  Interval.swift
//  PropertyFinder1
//
//  Created by K Arun Kumar Reddy on 19/12/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation

class Interval: Equatable, CustomStringConvertible {
    var description: String
    private var start: Int;
    private var end: Int;
    
    init(start: Int, end: Int) {
        self.start = start;
        self.end = end;
        self.description = start.description + "-" + end.description;
    }
    
    func getStart() -> Int {
        return self.start;
    }
    
    func getEnd() -> Int {
        return self.end;
    }
    
    func setStart(start: Int) {
        self.start = start;
    }
    
    func setEnd(end: Int) {
        self.end = end;
    }
    
    static func ==(a: Interval,b: Interval) -> Bool {
        return (a.getStart()==b.getStart() && a.getEnd()==b.getEnd());
    }
    
    func contains(interval: Interval) -> Bool {
        return (self.start <= interval.getStart() && self.end >= interval.getEnd());
    }
    
    func intersects(interval: Interval) -> Bool {
        return !(self.start >= interval.getEnd() || self.end <= interval.getStart());
    }
    
    func compareTo(interval: Interval) -> Int {
        if (self.start < interval.getStart()) {
            return -1;
        } else if (self.start > interval.getStart()) {
            return 1;
        } else {
            if (self.end == interval.getEnd()) {
                return 0;
            } else {
                return self.end < interval.getEnd() ? -1 : 1;
            }
        }
    }
    
}

//
//  IntervalNode.swift
//  PropertyFinder1
//
//  Created by K Arun Kumar Reddy on 19/12/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation

class IntervalNode {
    private var interval: Interval;
    private var left: IntervalNode?;
    private var right: IntervalNode?;
    
    init(interval: Interval) {
        self.interval = interval;
        self.left = nil;
        self.right = nil;
    }
    
    func getInterval() -> Interval {
        return self.interval;
    }
    
    func getLeft() -> IntervalNode? {
        return self.left;
    }
    
    func getRight() -> IntervalNode? {
        return self.right;
    }
    
    func setInterval(interval: Interval) {
        self.interval = interval;
    }
    
    func setLeft(node: IntervalNode?) {
        self.left = node;
    }
    
    func setRight(node: IntervalNode?) {
        self.right = node;
    }
}

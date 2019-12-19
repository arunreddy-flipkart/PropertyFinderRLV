//
//  IntervalTree.swift
//  PropertyFinder1
//
//  Created by K Arun Kumar Reddy on 19/12/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation

class IntervalTree {
    private var root: IntervalNode?;
    
    init() {
        self.root = nil;
    }
    
    func add(interval: Interval) {
        root = insert(root: root, interval: interval);
    }
    
    func remove(interval: Interval) {
        root = delete(root: root, interval: interval);
    }
    
    func searchAll(start: Int, end: Int) -> Array<Interval> {
        let searchInterval: Interval = Interval(start: start, end: end);
        var overlappingIntervals: Array<Interval> = Array();
        search(root: root, searchInterval: searchInterval, overlappingIntervals: &overlappingIntervals);
        return overlappingIntervals;
    }
    
    func inorder() -> Array<Interval> {
        var intervalList: Array<Interval> = Array();
        inorder(root: root, intervalList: &intervalList);
        return intervalList;
    }
    
    private func newNode(interval: Interval) -> IntervalNode {
        return IntervalNode(interval: interval);
    }
    
    private func search(root: IntervalNode?, searchInterval: Interval, overlappingIntervals: inout Array<Interval>) {
        if let node = root {
            let currentInterval: Interval = node.getInterval();
            let cmp: Int = searchInterval.compareTo(interval: currentInterval);
            if (searchInterval.intersects(interval: currentInterval)) {
                if (currentInterval.contains(interval: searchInterval)) {
                    overlappingIntervals.append(currentInterval);
                } else if (searchInterval.contains(interval: currentInterval)) {
                    search(root: node.getLeft(), searchInterval: searchInterval, overlappingIntervals: &overlappingIntervals);
                    overlappingIntervals.append(currentInterval);
                    search(root: node.getRight(), searchInterval: searchInterval, overlappingIntervals: &overlappingIntervals);
                } else if (cmp < 0) {
                    search(root: node.getLeft(), searchInterval: searchInterval, overlappingIntervals: &overlappingIntervals);
                    overlappingIntervals.append(currentInterval);
                } else {
                    overlappingIntervals.append(currentInterval);
                    search(root: node.getRight(), searchInterval: searchInterval, overlappingIntervals: &overlappingIntervals);
                }
            } else {
                if (cmp < 0) {
                    search(root: node.getLeft(), searchInterval: searchInterval, overlappingIntervals: &overlappingIntervals);
                } else {
                    search(root: node.getRight(), searchInterval: searchInterval, overlappingIntervals: &overlappingIntervals);
                }
            }
        }
    }
    
    private func inorder(root: IntervalNode?, intervalList: inout Array<Interval>) {
        if let node = root {
            inorder(root: node.getLeft(), intervalList: &intervalList);
            intervalList.append(node.getInterval());
            inorder(root: node.getRight(), intervalList: &intervalList);
        }
    }
    
    private func insert(root: IntervalNode?, interval: Interval) -> IntervalNode? {
        if let node = root {
            if (node.getInterval() == interval) {
                return node;
            }
            let cmp = interval.compareTo(interval: node.getInterval());
            if (cmp < 0) {
                node.setLeft(node: insert(root: node.getLeft(), interval: interval));
            } else {
                node.setRight(node: insert(root: node.getRight(), interval: interval));
            }
            return root;
        } else {
            return newNode(interval: interval);
        }
    }
    
    private func delete(root: IntervalNode?, interval: Interval) -> IntervalNode? {
        if let node = root {
            let cmp = interval.compareTo(interval: node.getInterval());
            if (cmp < 0) {
                node.setLeft(node: delete(root: node.getLeft(), interval: interval));
            } else if (cmp > 0) {
                node.setRight(node: delete(root: node.getRight(), interval: interval));
            } else {
                if (node.getLeft() == nil) {
                    return node.getRight();
                } else if (node.getRight() == nil) {
                    return node.getLeft();
                }
                let successor = inorderSuccssor(root: node);
                node.setInterval(interval: successor.getInterval());
                node.setRight(node: delete(root: node.getRight(), interval: successor.getInterval()));
            }
            return node;
        } else {
            return nil;
        }
    }
    
    private func inorderSuccssor(root: IntervalNode) -> IntervalNode {
        if var current = root.getRight() {
            while (true) {
                if let left = current.getLeft() {
                    current = left;
                } else {
                    break;
                }
            }
            return current;
        }
        return root;
    }
}

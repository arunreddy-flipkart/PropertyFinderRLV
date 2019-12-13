package com.propertyfinder1.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.List;

public class IntervalTree {
    private IntervalNode root;
    private Map<Interval, IntervalNode> currentIntervals;

    public IntervalTree() {
        root = null;
    }

    public void add(Interval interval) {
        root = insert(root, interval);
    }

    public void remove(Interval interval) {
        root = delete(root, interval);
    }

    public ArrayList<Interval> inorder() {
        ArrayList<Interval> intervalList = new ArrayList<>();
        inorder(root, intervalList);
        return intervalList;
    }

    public ArrayList<Interval> searchAll(int start, int end) {
        Interval searchInterval = new Interval(start, end);
        ArrayList<Interval> overlappingIntervals = new ArrayList<>();
        search(root, searchInterval, overlappingIntervals);
        return overlappingIntervals;
    }

    private IntervalNode newNode(Interval interval) {
        return new IntervalNode(interval);
    }

    private void inorder(IntervalNode node, ArrayList<Interval> intervalList) {
        if (node == null) {
            return;
        }
        inorder(node.getLeft(), intervalList);
        intervalList.add(node.getInterval());
        inorder(node.getRight(), intervalList);
    }

    private void search(IntervalNode node, Interval searchInterval, ArrayList<Interval> overlappingIntervals) {
        if (node == null) {
            return;
        }
        Interval currentInterval = node.getInterval();
        int cmp = searchInterval.compareTo(currentInterval);
        if (searchInterval.intersects(currentInterval)) {
            if (currentInterval.contains(searchInterval)) {
                overlappingIntervals.add(currentInterval);
            } else if (searchInterval.contains(currentInterval)) {
                search(node.getLeft(), searchInterval, overlappingIntervals);
                overlappingIntervals.add(currentInterval);
                search(node.getRight(), searchInterval, overlappingIntervals);
            } else if (cmp < 0) {
                search(node.getLeft(), searchInterval, overlappingIntervals);
                overlappingIntervals.add(currentInterval);
            } else {
                overlappingIntervals.add(currentInterval);
                search(node.getRight(), searchInterval, overlappingIntervals);
            }
        } else {
            if (cmp < 0) {
                search(node.getLeft(), searchInterval, overlappingIntervals);
            } else {
                search(node.getRight(), searchInterval, overlappingIntervals);
            }
        }
    }

    private IntervalNode insert(IntervalNode node, Interval interval) {
        if (node == null) {
            return newNode(interval);
        } else if (node.getInterval().equals(interval)) {
            return node;
        }
        int cmp = interval.compareTo(node.getInterval());
        if (cmp < 0) {
            node.setLeft(insert(node.getLeft(), interval));
        } else {
            node.setRight(insert(node.getRight(), interval));
        }
        return node;
    }

    private IntervalNode inorderSuccesor(IntervalNode root) {
        IntervalNode current = root.getRight();
        while (current !=  null && current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    private IntervalNode delete(IntervalNode node, Interval interval) {
        if(node == null) {
            return null;
        }
        int cmp = interval.compareTo(node.getInterval());
        if (cmp < 0) {
            node.setLeft(delete(node.getLeft(), interval));
        } else if (cmp > 0) {
            node.setRight(delete(node.getRight(), interval));
        } else {
            if (node.getLeft() == null) {
                return node.getRight();
            } else if(node.getRight() == null) {
                return node.getLeft();
            }
            IntervalNode successor = inorderSuccesor(node);
            node.setInterval(successor.getInterval());
            node.setRight(delete(node.getRight(), successor.getInterval()));
        }
        return node;
    }

}

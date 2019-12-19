package com.propertyfinder1.util;

public class IntervalNode {
    private Interval interval;
    private IntervalNode left, right;

    public IntervalNode(Interval interval) {
        this.interval = interval;
        this.left = null;
        this.right = null;
    }

    public Interval getInterval() {
        return interval;
    }

    public IntervalNode getLeft() {
        return left;
    }

    public IntervalNode getRight() {
        return right;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public void setLeft(IntervalNode left) {
        this.left = left;
    }

    public void setRight(IntervalNode right) {
        this.right = right;
    }
}

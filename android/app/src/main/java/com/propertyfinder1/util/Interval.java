package com.propertyfinder1.util;

public class Interval {
    private int start;
    private int end;

    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object obj) {
        Interval interval = (Interval)obj;
        return (this.start == interval.getStart() && this.end == interval.getEnd());
    }

    @Override
    public String toString() {
        return this.start+"-"+this.end;
    }

    public boolean contains(Interval interval) {
        return (this.start <= interval.getStart() && this.end >= interval.getEnd());
    }

    public boolean intersects(Interval interval) {
        return !(this.start >= interval.getEnd() || this.end <= interval.getStart());
    }

    public int compareTo(Interval interval) {
        if (this.start < interval.getStart()) {
            return -1;
        } else if (this.start > interval.getStart()) {
            return 1;
        } else {
            if (this.end == interval.getEnd()) {
                return 0;
            } else {
                return this.end < interval.getEnd() ? -1 : 1;
            }
        }
    }
}

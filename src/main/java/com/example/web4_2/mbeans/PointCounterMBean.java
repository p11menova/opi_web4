package com.example.web4_2.mbeans;


public interface PointCounterMBean {
    int getTotalPoints();
    int getMissedPoints();
    void addPoint(boolean hit);

}
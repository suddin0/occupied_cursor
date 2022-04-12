package com.occupiedcursor.helper;

public class Point2DData {
    private double x;
    private double y;

    public Point2DData() {
        this(0.0, 0.0);
    }

    public Point2DData(double x, double y) {
        this.x = x;
        this.y = y ;
    }

    public Point2DData(Point2DData point) {
        setX(point.getX());
        setY(point.getY());
    }

    public void set2DPoint(double x, double y) {
        setX(x);
        setY(y);
    }

    public void set2DPoint(Point2DData point) {
        setX(point.getX());
        setY(point.getY());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}

package com.occupiedcursor.helper;

public class ScreenData {
    private double height = 0.0;
    private double width = 0.0;
    private Point2DData leftPosition;
    private Point2DData rightPosition;

    public Point2DData getCenter() {
        if(leftPosition == null || rightPosition == null) {
            return null;
        }
        double horizontalCenter = leftPosition.getX() + (width / 2.0);
        double verticalCenter = leftPosition.getY() + (height / 2.0);
        Point2DData center = new Point2DData(horizontalCenter, verticalCenter);

        return center;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Point2DData getLeftPosition() {
        return leftPosition;
    }

    public void setLeftPosition(Point2DData leftPosition) {
        this.leftPosition = leftPosition;
    }

    public Point2DData getRightPosition() {
        return rightPosition;
    }

    public void setRightPosition(Point2DData rightPosition) {
        this.rightPosition = rightPosition;
    }
}

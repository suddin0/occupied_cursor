package com.occupiedcursor.utils;


import com.occupiedcursor.helper.Point2DData;
import com.occupiedcursor.helper.ScreenData;

import java.util.Timer;
import java.util.TimerTask;

public abstract class ASimpleMouseMovementBase implements Runnable {
    private Timer timer;
    private ScreenData screen;
    private Point2DData cursorStartPosition;
    private Point2DData cursorEndPosition;
    private Point2DData cursorCurrentPosition;
    private int interval;
    private boolean alternate;
    private TimerTask timerTask;
    private volatile boolean exit;

    public ASimpleMouseMovementBase(ScreenData screen) {
        cursorStartPosition = new Point2DData(0.0, 0.0);
        cursorEndPosition = new Point2DData(0.0, 0.0);
        cursorCurrentPosition = new Point2DData(cursorStartPosition);
        this.screen = screen;
        interval = 700;
    }

    public void stop() {
        this.exit = true;
        timerTask.cancel();
        timer.cancel();
        timer.purge();
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public ScreenData getScreen() {
        return screen;
    }

    public void setScreen(ScreenData screen) {
        this.screen = screen;
    }

    public Point2DData getCursorStartPosition() {
        return cursorStartPosition;
    }

    public void setCursorStartPosition(Point2DData cursorStartPosition) {
        this.cursorStartPosition = cursorStartPosition;
    }

    public Point2DData getCursorEndPosition() {
        return cursorEndPosition;
    }

    public void setCursorEndPosition(Point2DData cursorEndPosition) {
        this.cursorEndPosition = cursorEndPosition;
    }

    public Point2DData getCursorCurrentPosition() {
        return cursorCurrentPosition;
    }

    public void setCursorCurrentPosition(Point2DData cursorCurrentPosition) {
        this.cursorCurrentPosition = cursorCurrentPosition;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public boolean isAlternate() {
        return alternate;
    }

    public void setAlternate(boolean alternate) {
        this.alternate = alternate;
    }

    public TimerTask getTimerTask() {
        return timerTask;
    }

    public void setTimerTask(TimerTask timerTask) {
        this.timerTask = timerTask;
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}

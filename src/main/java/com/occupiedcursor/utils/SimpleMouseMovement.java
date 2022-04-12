package com.occupiedcursor.utils;

import com.occupiedcursor.helper.Point2DData;
import com.occupiedcursor.helper.ScreenData;
import java.util.Timer;
import java.util.TimerTask;

public class SimpleMouseMovement {

    public static abstract class Horizontal extends ASimpleMouseMovementBase{


        public Horizontal(ScreenData screen){
            super(screen);
            setExit(false);
            setCursorStartPosition(new Point2DData(screen.getCenter().getX() - (screen.getWidth() / 4.0), screen.getCenter().getY()));
            setCursorEndPosition(new Point2DData(screen.getCenter().getX() + (screen.getWidth() / 4.0), screen.getCenter().getY()));
            getCursorCurrentPosition().set2DPoint(getCursorStartPosition());
            setAlternate(true);
            setInterval(1);
        }

        @Override
        public void run() {
            setTimer(new Timer());
            TimerTask myTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if(isExit() == true) {
                        return;
                    }

                    moveCursor(getCursorCurrentPosition().getX(), getCursorCurrentPosition().getY());

                    /* if we must alternate to right */
                    if(isAlternate() == true) {
                        getCursorCurrentPosition().setX(getCursorCurrentPosition().getX() + 1);
                        /* If we have arrived to the end of the alternating limit then reverse*/
                        if(getCursorCurrentPosition().getX() >= getCursorEndPosition().getX()) {
                            setAlternate(false);
                        }
                    } else {
                        getCursorCurrentPosition().setX(getCursorCurrentPosition().getX() - 1);
                        if(getCursorCurrentPosition().getX() <= getCursorStartPosition().getX()) {
                            setAlternate(true);
                        }
                    }

                    /* if for some reason we are out of bound */
                    if(getCursorCurrentPosition().getX() > getCursorEndPosition().getX()
                            || getCursorCurrentPosition().getX() < getCursorStartPosition().getX()) {
                        return;
                    }
                }
            };

            setTimerTask(myTimerTask);
            getTimer().scheduleAtFixedRate(getTimerTask(), 0, getInterval());
        }


        protected abstract void moveCursor(double x, double y);
    }
}

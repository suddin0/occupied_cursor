package com.occupiedcursor.controller;

import com.occupiedcursor.helper.Point2DData;
import com.occupiedcursor.helper.ScreenData;
import com.occupiedcursor.utils.SimpleMouseMovement;
import com.occupiedcursor.views.SimpleView;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.robot.Robot;
import javafx.stage.Screen;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class SimpleViewController {

    private SimpleView view;
    private BooleanProperty startState;
    private Thread mouseMovementThread;
    private SimpleMouseMovement.Horizontal mouceMovementJob;
    private ObservableList<Screen> screens;
    private Screen selectedScreen;
    private final double SLIDER_VALUE_MAX = 200.0;
    private final double SLIDER_VALUE_MIN = 1;
    private final double SLIDER_VALUE_DEFAULT = SLIDER_VALUE_MAX;
    private Point2D mouseSavedPosition;
    private Timer timer;
    private TimerTask timerTask;

    private long countDown;
    private long baseCountDown;
    private long tick;

    public SimpleViewController(SimpleView view) {

        this.view = view;
        this.countDown = 0l;
        this.tick = 0l;
        this.screens = Screen.getScreens();
        this.selectedScreen = screens.get(0);
        this.startState = new SimpleBooleanProperty(this, "start",false);
        this.mouseSavedPosition = null;
        this.setDefaultState();
    }

    /**
     * The following function sets the cursor position in screen.
     * @param x the X coordinate in the screen
     * @param y the Y coordinate in the screen
     */
    private void setCursor(double x, double y) {

        Platform.runLater(() -> {
            Robot robot = new Robot();

            robot.mouseMove(x, y);
        });
    }

    private void setDefaultState() {
        /* if any key is pressed */
        this.view.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(startState.getValue() == true) {
                stop();
            }
        });

        /* if any mouse key is pressed */
        this.view.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(startState.getValue() == true) {
                stop();
            }
        });

        /* Set timer area */
        view.timerType.setValue("m");
        view.timerType.setOnAction(event -> {this.view.timerTextField.setText("");});
        view.timerTextField.setTextFormatter(new TextFormatter<Object>(change -> {
            /* of the entered content is not text */
            if(change.getText().isBlank()) {
                return change;
            } else if (change.getText().matches("[-+]?\\d*\\.?\\d+")) {
                /* First character can not be zero */
                if(change.getControlNewText().length() == 1 && change.getText().equals("0")) {
                    return null;
                }

                /* verify the upper limit of the minutes */
                if(view.timerType.getValue().equals("m")) {
                    int minutes = 0;
                    try {
                        minutes = Integer.parseInt(change.getControlNewText());
                        if(minutes > 10080) {
                            return null;
                        } else {
                            return change;
                        }
                    } catch (Exception e) {
                        System.err.println("Error : converting text to number in TextFormatter (\"m\") : " + e);
                        return null;
                    }
                }

                /* verify the upper limit of the hours */
                if(view.timerType.getValue().equals("h")) {
                    int minutes = 0;
                    try {
                        minutes = Integer.parseInt(change.getControlNewText());
                        if(minutes > 168) {
                            return null;
                        } else {
                            return change;
                        }
                    } catch (Exception e) {
                        System.err.println("Error : converting text to number in TextFormatter (\"h\") : " + e);
                        return null;
                    }
                }
                return change;
            } else {
                return null;
            }
        }));

        /* set monitor area */
        if(screens.size() == 1) {
            disableMonitorArea();
        } else {
            for (int monitorIterator = 0; monitorIterator < screens.size(); monitorIterator++) {
                view.monitorComboBox.getItems().add((monitorIterator + 1) + "");
            }
            view.monitorComboBox.setValue("1");
            view.monitorComboBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int selectedScreenValue = Integer.parseInt(view.monitorComboBox.getValue()) - 1;
                    selectedScreen = screens.get(selectedScreenValue);
                }
            });
        }

        /* speed functionality */
        view.speedSlider.setMax(SLIDER_VALUE_MAX);
        view.speedSlider.setMin(SLIDER_VALUE_MIN);

        view.speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number > observable, Number oldValue, Number newValue)
            {
                view.setSpeedLabelValue(newValue.doubleValue());
                view.speedSlider.setValue(Math.floor(newValue.doubleValue()));
            }
        });
        view.speedSlider.setValue(SLIDER_VALUE_DEFAULT);

        startState.addListener((v, oldValue, newValue) -> {
            if(newValue == false) {
                enableTopButtons();
                enableTimerArea();
                enableMonitorArea();
                enableSpeedArea();
            } else {
                disableTopButtons();
                disableTimerArea();
                disableMonitorArea();
                disableSpeedArea();
            }
        });

        /* Start Button functionality */
        view.startButton.setOnAction(event -> {
            /* If the start button is pressed to start */
            if(this.startState.getValue() == false) {
                this.start();
            } else {
                this.stop();
            }
        });
    }

    /**
     * The following function stops and clear any mouse movement releted thread if exists.
     */
    private void clearMouseMovementThread() {
        if(mouseMovementThread != null) {
            if(mouceMovementJob != null) {
                mouceMovementJob.stop();
                mouceMovementJob = null;
            }
            mouseMovementThread.interrupt();
            mouseMovementThread = null;
        }
    }

    /**
     * The following function is used to start the mouse movement. It also manages the timer execution.
     */
    private void start() {

        /* save the curren cursor position to reset it later */
        Robot robot = new Robot();
        this.mouseSavedPosition = robot.getMousePosition();

        System.out.println(" Houre or minute : " + view.timerType.getValue());

        this.startState.set(true);
        view.setStopButtonStyles();
        view.startButton.setText("Press any keys to stop");
        mouceMovementJob = new SimpleMouseMovement.Horizontal(screenDataScraper(selectedScreen)) {
            @Override
            protected void moveCursor(double x, double y) {
                setCursor(x, y);
            }
        };
        mouceMovementJob.setInterval((int) (SLIDER_VALUE_MAX - view.speedSlider.getValue()) + 1);
        mouseMovementThread = new Thread(mouceMovementJob);
        mouseMovementThread.start();

        if(view.timerTextField.getText().isBlank() == false) {
            long textFieldValue  = 0;
            try {
                textFieldValue = Long.parseLong(view.timerTextField.getText());
                if(view.timerType.getValue().equals("h")) {
                    /* convert hours to seconds */
                    this.baseCountDown = textFieldValue * 60 * 60;
                } else {
                    /* convert minutes to seconds */
                    this.baseCountDown = textFieldValue * 60;
                }
                this.countDown = 0;
                startTimer();
            } catch (Exception e) {
                System.err.println("Error : While converting timer text to integer : " + e);
            }

        }
    }

    /**
     * The following function stops anything that is started, including the timer. It also does some cleanups and
     * restore some parameters such as cursor position and values of some variables.
     */
    private void stop() {
        this.startState.set(false);
        this.stopTimer();
        view.setStartButtonStyles();
        view.startButton.setText("Start");
        clearMouseMovementThread();

        if(this.mouseSavedPosition != null) {
            setCursor(this.mouseSavedPosition.getX(), this.mouseSavedPosition.getY());
            mouseSavedPosition = null;
        }
    }

    /**
     * The following function start the timer.
     */
    private void startTimer() {
        if(this.baseCountDown == 0) {
            return;
        }

        if(timer != null) {
            stopTimer();
        }

        timerTask = new TimerTask() {
            @Override
            public void run() {
                if((tick % 60) == 0) {
                    countDown++;
                }

                if(countDown >= baseCountDown) {
                    stopPlatformRunLater();
                    stopTimer();
                } else {
                    //countDown++;
                    tickTimer();
                }
                tick++;
            }
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);

    }

    /**
     * This function stops the timer and do some cleanups
     */
    private void stopTimer() {

        this.view.timerProgressBar.setProgress(0);
        if(view.timerType.getValue().equals("h")) {
            this.view.timerTextField.setText(((baseCountDown / 60) / 60) + "");
        } else {
            this.view.timerTextField.setText((baseCountDown / 60) + "");
        }

        baseCountDown = 0;
        countDown = 0;
        tick = 0;

        if(timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if(timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    /**
     * The following function is called from `run` function of TimerTask to stop the timer itself.
     */
    private void stopPlatformRunLater() {
        Platform.runLater(() -> {
            stop();
        });
    }

    /**
     * This function is called each time the timer the `run` function is called inside the TimerTask. It is mostly used
     * to show the remaining time visually in the progress bar and change the time value.
     */
    private void tickTimer() {
        double purcentage = ((double) ((tick * 100) / baseCountDown)) / 100.0;

        Platform.runLater(() -> {
            if(view.timerType.getValue().equals("h")) {
                long base = TimeUnit.SECONDS.toHours(baseCountDown);
                long passed =  TimeUnit.SECONDS.toHours(tick);
                this.view.timerTextField.setText((base - passed) + "");
            } else {
                long base = TimeUnit.SECONDS.toMinutes(baseCountDown);
                long passed =  TimeUnit.SECONDS.toMinutes(tick);
                this.view.timerTextField.setText((base - passed) + "");
            }
            this.view.timerProgressBar.setProgress(purcentage);
        });
    }

    private void enableTopButtons() {
        view.topButtonGroup.setDisable(false);
    }

    private void disableTopButtons() {
        view.topButtonGroup.setDisable(true);
    }

    private void enableTimerArea() {
        view.timerGroup.setDisable(false);
    }

    private void disableTimerArea() {
        view.timerGroup.setDisable(true);
    }

    private void enableMonitorArea() {
        if(screens.size() > 1) {
            view.monitorGroup.setDisable(false);
        }
    }

    private void disableMonitorArea() {
        view.monitorGroup.setDisable(true);
    }

    private void enableSpeedArea() {
        view.speedGroup.setDisable(false);
    }

    private void disableSpeedArea() {
        view.speedGroup.setDisable(true);
    }

    private ScreenData screenDataScraper(Screen screen) {
        ScreenData screenData = new ScreenData();
        screenData.setWidth(selectedScreen.getVisualBounds().getWidth());
        screenData.setHeight(selectedScreen.getVisualBounds().getHeight());
        screenData.setLeftPosition(new Point2DData(selectedScreen.getBounds().getMinX(), selectedScreen.getBounds().getMinY()));
        screenData.setRightPosition(new Point2DData(selectedScreen.getBounds().getMaxX(), selectedScreen.getBounds().getMaxY()));
        return screenData;
    }
}

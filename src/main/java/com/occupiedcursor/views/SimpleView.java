package com.occupiedcursor.views;

import com.occupiedcursor.components.HeaderComponent;
import com.occupiedcursor.components.LabelComponent;
import com.occupiedcursor.controller.SimpleViewController;
import com.occupiedcursor.helper.ColorPalette;
import com.occupiedcursor.styles.Styles;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class SimpleView extends VBox {

    private SimpleViewController controller;

    private Styles viewStyles;
    private Styles startButtonStyles;
    private Styles startButtonBaseStyles;
    private Styles stopButtonStyles;

    private HeaderComponent header;
    public Button startButton;

    /* top button option */
    public  Group topButtonGroup;
    private Button simpleButton;
    private Button customButton;
    private Styles simpleButtonStyles;
    private Styles customButtonStyles;
    private Styles topButtonStyles;
    private HBox topButtonOptionContainer;

    /* Timer */
    public Group timerGroup;
    private AnchorPane timerContainer;
    private Styles timerContainerStyles;
    private HBox timerInputAreaContainer;
    private Styles timerInputAreaContainerStyles;
    private ImageView timerImageView;
    public ComboBox<String> timerType;
    public TextField timerTextField;
    private Styles timerTextFieldStyles;
    private LabelComponent timerLabel;
    public ProgressBar timerProgressBar;
    private Styles timerProgressBarStyles;

    /* Monitor */
    public Group monitorGroup;
    private AnchorPane monitorContainer;
    private HBox monitorSelectionAreaContainer;
    private Styles monitorContainerStyles;
    private Styles monitorSelectionAreaContainerStyles;
    private LabelComponent monitorLabel;
    private ImageView monitorImageView;
    public ComboBox<String> monitorComboBox;

    /* Speed */
    public Group speedGroup;
    private AnchorPane speedContainer;
    private Styles speedContainerStyles;
    private HBox speedAreaContainer;
    private Styles speedAreaContainerStyles;
    private LabelComponent speedLabel;
    private ImageView speedImageView;
    public Slider speedSlider;
    private final String speedLabelText = "Movement Speed";


    public SimpleView() {
        super();

        /* style related */
        this.viewStyles = new Styles();
        this.startButtonStyles = new Styles();
        this.startButtonBaseStyles = new Styles();
        this.stopButtonStyles = new Styles();

        /* Component related */
        this.header = new HeaderComponent();
        this.startButton = new Button("START");

        /* top button option */
        topButtonGroup = new Group();
        simpleButton = new Button("Simple");
        customButton = new Button("Custom");
        simpleButtonStyles = new Styles();
        customButtonStyles = new Styles();
        topButtonStyles = new Styles();
        topButtonOptionContainer = new HBox();

        /* timer related */
        timerGroup = new Group();
        timerContainer = new AnchorPane();
        timerContainerStyles = new Styles();
        timerInputAreaContainer = new HBox();
        timerInputAreaContainerStyles = new Styles();
        timerImageView = new ImageView();
        timerType = new ComboBox<>();
        timerTextField = new TextField();
        timerTextFieldStyles = new Styles();
        timerLabel = new LabelComponent("Timer");
        timerProgressBar = new ProgressBar(0.0);
        timerProgressBarStyles = new Styles();

        /* Monitor related */
        monitorGroup = new Group();
        monitorContainer = new AnchorPane();
        monitorSelectionAreaContainer = new HBox();
        monitorContainerStyles = new Styles();
        monitorSelectionAreaContainerStyles = new Styles();
        monitorLabel = new LabelComponent("Monitor");
        monitorImageView = new ImageView();
        monitorComboBox = new ComboBox<String>();

        /* Speed related*/
        speedGroup = new Group();
        speedContainer = new AnchorPane();
        speedContainerStyles = new Styles();
        speedAreaContainer = new HBox();
        speedAreaContainerStyles = new Styles();
        speedLabel = new LabelComponent(speedLabelText);
        speedImageView = new ImageView();
        speedSlider = new Slider(0, 100, 0.5);


        this.setStyles();
        this.setStyle(this.viewStyles.toString());
        this.setAlignment(Pos.TOP_CENTER);
        this.setFillWidth(true);

        create();
        setController();
    }

    private void create() {

        double LABEL_MARGIN_TOP = 25.0;

        // 01. The title


        // 02. The simple and custom buttons
        VBox.setMargin(topButtonOptionContainer, new Insets(40, 0, 0 ,0));
        simpleButton.setStyle(simpleButtonStyles.toString() + topButtonStyles.toString());
        customButton.setStyle(customButtonStyles.toString() + topButtonStyles.toString());
        topButtonOptionContainer.setAlignment(Pos.CENTER);
        topButtonOptionContainer.setSpacing(10);
        topButtonOptionContainer.getChildren().addAll(simpleButton, customButton);
        topButtonGroup.getChildren().add(topButtonOptionContainer);


        // 03. The timer
        Image timerIcon = getImage("timer-icon.png");
        if(timerIcon == null) {
            System.err.println("[-] Error : Exiting program due to image loading error. (timer-icon)");
            System.exit(1);
        } else {
            timerImageView.setImage(timerIcon);
            timerImageView.setFitHeight(23);
            timerImageView.setFitWidth(23);
        }

        VBox.setMargin(timerGroup, new Insets(40, 0, 0 ,0));
        timerTextField.setPromptText("Undefined");
        timerTextField.setStyle(timerTextFieldStyles.toString());
        timerTextField.setAlignment(Pos.CENTER);
        timerTextField.cancelEdit();

        timerInputAreaContainer.setStyle(timerInputAreaContainerStyles.toString());
        timerInputAreaContainer.setAlignment(Pos.CENTER_LEFT);
        timerInputAreaContainer.setSpacing(10);
        timerInputAreaContainer.getChildren().addAll(timerImageView, timerTextField);

        timerType.getItems().addAll("m", "h");
        timerType.setPromptText("m");

        AnchorPane.setTopAnchor(timerLabel, 0.0);
        AnchorPane.setLeftAnchor(timerLabel, 0.0);
        AnchorPane.setLeftAnchor(timerInputAreaContainer, 0.0);
        AnchorPane.setTopAnchor(timerInputAreaContainer, LABEL_MARGIN_TOP);
        AnchorPane.setRightAnchor(timerType, 0.0);
        AnchorPane.setTopAnchor(timerType, LABEL_MARGIN_TOP);
        //AnchorPane.setBottomAnchor(timerProgressBar, 0.0);
        AnchorPane.setTopAnchor(timerProgressBar, 113.0);


        timerContainer.setStyle(timerContainerStyles.toString());
        timerContainer.getChildren().addAll(timerLabel, timerInputAreaContainer, timerType, timerProgressBar);
        timerGroup.getChildren().add(timerContainer);



        // 04. the monitor

        Image monitorIcon = getImage("monitor-icon.png");
        if(monitorIcon == null) {
            System.err.println("[-] Error : Exiting program due to image loading error. (monitor-icon)");
            System.exit(1);
        } else {
            monitorImageView.setImage(monitorIcon);
            monitorImageView.setFitHeight(23);
            monitorImageView.setFitWidth(23);
        }

        monitorComboBox.setPromptText("1");
        VBox.setMargin(monitorGroup, new Insets(40, 0, 0 ,0));
        monitorSelectionAreaContainer.setStyle(monitorContainerStyles.toString());
        monitorSelectionAreaContainer.setAlignment(Pos.CENTER_LEFT);
        monitorSelectionAreaContainer.setSpacing(10);
        monitorSelectionAreaContainer.getChildren().addAll(monitorImageView, monitorComboBox);

        AnchorPane.setTopAnchor(monitorLabel, 0.0);
        AnchorPane.setLeftAnchor(monitorLabel, 0.0);
        AnchorPane.setTopAnchor(monitorSelectionAreaContainer, LABEL_MARGIN_TOP);
        AnchorPane.setLeftAnchor(monitorSelectionAreaContainer, 0.0);

        monitorContainer.getChildren().addAll(monitorLabel, monitorSelectionAreaContainer);
        monitorGroup.getChildren().add(monitorContainer);


        // 05. The speed

        Image speedIcon = getImage("speed-icon.png");
        if(speedIcon == null) {
            System.err.println("[-] Error : Exiting program due to image loading error. (speed-icon)");
            System.exit(1);
        } else {
            speedImageView.setImage(speedIcon);
            speedImageView.setFitHeight(23);
            speedImageView.setFitWidth(23);
        }

        //VBox.setMargin(speedGroup, new Insets(40 + 53, 0, 0 ,0));
        VBox.setMargin(speedGroup, new Insets(40, 0, 0 ,0));
        speedAreaContainer.setStyle(speedContainerStyles.toString());
        speedAreaContainer.setAlignment(Pos.CENTER_LEFT);
        speedAreaContainer.setSpacing(10);
        speedAreaContainer.getChildren().addAll(speedImageView, speedSlider);

        //speedSlider.setShowTickLabels(true);

        AnchorPane.setTopAnchor(speedLabel, 0.0);
        AnchorPane.setLeftAnchor(speedLabel, 0.0);
        AnchorPane.setTopAnchor(speedAreaContainer, LABEL_MARGIN_TOP);
        AnchorPane.setLeftAnchor(speedAreaContainer, 0.0);

        speedContainer.getChildren().addAll(speedLabel, speedAreaContainer);
        speedGroup.getChildren().add(speedContainer);



        // 06. the start button
        VBox.setMargin(startButton, new Insets(60, 0, 0 ,0));
        setStartButtonStyles();

        /* setting all the components */
        this.getChildren().add(header);

        /* The following comment shall remain till the << custom >> part is developed */
        // this.getChildren().add(topButtonGroup);

        this.getChildren().add(timerGroup);
        this.getChildren().add(monitorGroup);
        this.getChildren().add(speedGroup);
        this.getChildren().add(startButton);
    }

    public void setStartButtonStyles() {
        this.startButton.setStyle(this.startButtonBaseStyles.toString() + this.startButtonStyles.toString());
    }

    public void setStopButtonStyles() {
        this.startButton.setStyle(this.startButtonBaseStyles.toString() + this.stopButtonStyles.toString());
    }

    private void setController() {
        controller = new SimpleViewController(this);
    }

    private Image getImage(String name) {
        Image image = null;
        try {
            URL file = getClass().getClassLoader().getResource(name);
            if (file == null) {
                System.err.println("[-] Error : opening '" + name + "'. (File not found)");
                return null;
            }
            image = new Image(file.openStream());
        } catch (FileNotFoundException e) {
            System.err.println("[-] Error : opening '" + name + "'. (File not found)");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.err.println("[-] Error : opening '" + name + "'.");
            e.printStackTrace();
            return null;
        } catch (NullPointerException e) {
            System.err.println("[-] Error : opening '" + name + "'. (NullPointer Exception)");
            e.printStackTrace();
            return null;
        }
        return image;
    }

    public void setSpeedLabelValue(double val) {
        double purcentage =  (speedSlider.getValue() * 100.0) /  speedSlider.getMax();
        speedLabel.setText(speedLabelText + " " + ((int) purcentage) + "%");
    }

    void setStyles() {
        /* View styles */
        this.viewStyles.add("-fx-background-color", ColorPalette.BACKGROUND);

        /* Top button styles */
        // "-fx-min-width: 200; -fx-min-height: 43; -fx-background-color: #232832; -fx-text-fill: white; -fx-font-size: 18; -fx-font-weight: bold"
        topButtonStyles.add("-fx-min-width", "200");
        topButtonStyles.add("-fx-min-height", "43");
        topButtonStyles.add("-fx-text-fill", ColorPalette.TEXT_ACTIVE);
        topButtonStyles.add("-fx-font-size", "16");
        topButtonStyles.add("-fx-font-weight", "bold");
        topButtonStyles.add("-fx-font-family", "Roboto-Regular");
        topButtonStyles.add("-fx-background-radius", "11");
        topButtonStyles.add("-fx-border-radius", "11");
        simpleButtonStyles.add("-fx-background-color", ColorPalette.CONTROL_ACTIVE);
        customButtonStyles.add("-fx-background-color", ColorPalette.BACKGROUND);

        /* Button styles */
        this.startButtonStyles.add("-fx-background-color", ColorPalette.PRIMARY);
        this.stopButtonStyles.add("-fx-background-color", "#ff5f52");
        this.startButtonBaseStyles.add("-fx-min-width", "410");
        this.startButtonBaseStyles.add("-fx-min-height", "75");
        this.startButtonBaseStyles.add("-fx-border-radius", "8");
        this.startButtonBaseStyles.add("-fx-background-radius", "8");
        this.startButtonBaseStyles.add("-fx-text-fill", ColorPalette.TEXT_ACTIVE);
        this.startButtonBaseStyles.add("-fx-font-size", "18");
        this.startButtonBaseStyles.add("-fx-font-weight", "bold");

        /* Timer related */
        timerInputAreaContainerStyles.add("-fx-background-color", ColorPalette.CONTROL_DEFAULT);
        timerInputAreaContainerStyles.add("-fx-padding", "0 20 0 20");
        timerInputAreaContainerStyles.add("-fx-min-width", "321");
        timerInputAreaContainerStyles.add("-fx-max-width", "321");
        timerInputAreaContainerStyles.add("-fx-min-height", "83");
        timerInputAreaContainerStyles.add("-fx-max-height", "83");
        timerInputAreaContainerStyles.add("-fx-background-radius", "6");

        timerTextFieldStyles.add("-fx-min-width", "260");
        timerTextFieldStyles.add("-fx-min-height", "70");
        timerTextFieldStyles.add("-fx-font-size", "24");
        timerTextFieldStyles.add("-fx-text-fill", ColorPalette.TEXT_ACTIVE);
        timerTextFieldStyles.add("-fx-background-color", ColorPalette.CONTROL_DEFAULT);
        timerTextFieldStyles.add("-fx-font-weight", "bold");

        timerProgressBar.getStyleClass().add("timer-progress-bar");
        timerType.getStyleClass().add("timer-combo-box");

        timerContainerStyles.add("-fx-min-width", "410");

        /* Monitor related */
        monitorContainerStyles.add("-fx-background-color", ColorPalette.CONTROL_DEFAULT);
        monitorContainerStyles.add("-fx-padding", "0 20 0 20");
        monitorContainerStyles.add("-fx-min-width", "410");
        monitorContainerStyles.add("-fx-max-width", "410");
        monitorContainerStyles.add("-fx-min-height", "83");
        monitorContainerStyles.add("-fx-max-height", "83");
        monitorContainerStyles.add("-fx-background-radius", "6");

        monitorComboBox.getStyleClass().add("monitor-combo-box");

        /* Speed related */
        //speedContainerStyles.add("-fx-background-color", ColorPalette.CONTROL_DEFAULT);
        //speedContainerStyles.add("-fx-background-color", "gray");
        speedContainerStyles.add("-fx-padding", "0 20 0 0");
        speedContainerStyles.add("-fx-min-width", "410");
        speedContainerStyles.add("-fx-max-width", "410");
        speedContainerStyles.add("-fx-min-height", "30");
        speedContainerStyles.add("-fx-max-height", "30");
        //speedContainerStyles.add("-fx-background-radius", "6");

    }
}

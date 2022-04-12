package com.occupiedcursor.components;

import com.occupiedcursor.helper.ColorPalette;
import com.occupiedcursor.styles.Styles;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class LabelComponent extends Label {

    private Styles styles;


    public LabelComponent(String label) {
        this.styles = new Styles();

        this.setLabel(label);
        setStyles();
        create();
    }

    public LabelComponent() {
        this("");
    }

    public void setLabel(String text) {
        this.setText(text);
    }

    private void create() {
        // this.setStyle(this.styles.toString());
        this.setStyle(this.styles.toString());
        this.setTextFill(Color.web(ColorPalette.LABEL));

    }

    private void setStyles() {

        styles.add("-fx-font-size", "15");
        styles.add("-fx-fill", ColorPalette.LABEL);
    }
}

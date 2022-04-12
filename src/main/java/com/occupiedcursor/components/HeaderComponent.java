package com.occupiedcursor.components;

import com.occupiedcursor.helper.ColorPalette;
import com.occupiedcursor.styles.Styles;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class HeaderComponent extends VBox {
    private HBox headerContainer;
    private Styles headerContainerStyle;
    private Image appIcon = null;
    private ImageView appIconContainer;
    private Text title;
    private Styles titleStyles;
    private Styles iconStyles;

    public HeaderComponent() {
        super();
        this.headerContainer = new HBox();
        this.headerContainerStyle = new Styles();
        this.titleStyles = new Styles();
        this.iconStyles = new Styles();
        this.title = new Text("OCCUPIED CURSOR");
        this.appIconContainer = new ImageView();

        this.appIcon = getImage("app-icon.png");



        setStyles();
        create();
        attachController();
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
        } catch (NullPointerException e) {
            System.err.println("[-] Error : opening '" + name + "'. (NullPointer Exception)");
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            System.err.println("[-] Error : opening '" + name + "'. (File not found)");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.err.println("[-] Error : opening '" + name + "'.");
            e.printStackTrace();
            return null;
        }

        return image;
    }

    private void create() {

        this.title.setStyle(titleStyles.toString());

        this.headerContainer.setAlignment(Pos.CENTER);
        this.headerContainer.setSpacing(10);
        this.headerContainer.setStyle(this.headerContainerStyle.toString());

        if(appIcon != null) {
            appIconContainer.setFitWidth(35);
            appIconContainer.setFitHeight(35);
            appIconContainer.setImage(appIcon);
            this.headerContainer.getChildren().add(appIconContainer);
        }

        this.headerContainer.getChildren().add(title);
        this.getChildren().add(headerContainer);

    }

    private void attachController() {

    }

    void setStyles() {
        this.titleStyles.add("-fx-fill", ColorPalette.TEXT_ACTIVE);
        this.titleStyles.add("-fx-text-fill", "white");
        this.titleStyles.add("-fx-font-size", "36");
        this.titleStyles.add("-fx-font-weight", "bold");
        this.headerContainerStyle.add("-fx-padding", "30 0 10 0");
        //this.titleStyles.add("", "");
    }
}

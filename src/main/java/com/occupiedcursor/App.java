package com.occupiedcursor;

import com.occupiedcursor.configs.WindowConfig;
import com.occupiedcursor.views.SimpleView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        initStage(stage);
        URL cssStyles = getFile("styles.css");

        Scene scene = new Scene(new SimpleView(), WindowConfig.width, WindowConfig.height);
        if(cssStyles != null) {
            scene.getStylesheets().add(cssStyles.toExternalForm());
        }


        stage.setScene(scene);
        stage.show();
    }

    private void initStage(Stage stage) {
        if(stage == null) {
            return;
        }

        InputStream appIcon = getFileStream("app-icon.png");
        InputStream cssStyles = getFileStream("styles.css");


        if(!WindowConfig.windowDecoration) {
            stage.initStyle(StageStyle.TRANSPARENT);
        }

        stage.setResizable(WindowConfig.resizable);

        stage.setTitle("Occupied Cursor");

        if(appIcon != null) {
            stage.getIcons().add(new Image(appIcon));
        }

    }

    private URL getFile(String name) {
        URL file = null;
        try {
             file = getClass().getClassLoader().getResource(name);
            if (file == null) {
                System.err.println("[-] Error : opening '" + name +"'. (File not found)");
                return null;
            }
        } catch (NullPointerException e) {
            System.err.println("[-] Error : opening '" + name + "'. (NullPointer Exception)");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("[-] Error : opening '" + name +"' for application.");
            e.printStackTrace();
        }
        return file;
    }

    private InputStream getFileStream(String name) {
        URL file = getFile(name);
        InputStream fileStream = null;

        if(file == null) {
            return null;
        }

        try {
            fileStream = file.openStream();

        } catch (IOException e) {
            System.err.println("[-] Error : opening '" + name +"' as stream.");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("[-] Error : opening '" + name + " as stream'. (NullPointer Exception)");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("[-] Error : opening '" + name +" as stream' for application.");
            e.printStackTrace();
        }

        return fileStream;
    }

    public static void main(String[] args) {
        launch();
    }
}
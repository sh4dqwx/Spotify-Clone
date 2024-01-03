package pl.pb.spotifyclone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Hello!");
        stage.setScene(scene);

        ViewManager viewManager = ViewManager.getInstance();
        viewManager.setStage(stage);
        viewManager.switchView("home-view.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}
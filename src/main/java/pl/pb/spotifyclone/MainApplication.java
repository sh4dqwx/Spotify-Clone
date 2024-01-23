package pl.pb.spotifyclone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent mainView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-view.fxml")));
        Scene scene = new Scene(mainView, 1400, 800);
        stage.setTitle("SpotifyClone");
        stage.setScene(scene);

        ViewManager viewManager = ViewManager.getInstance();
        Parent homeView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-view.fxml")));
        viewManager.setStage(stage);
        viewManager.switchView(homeView);
    }

    public static void main(String[] args) {
        launch();
    }
}

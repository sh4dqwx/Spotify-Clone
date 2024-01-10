package pl.pb.spotifyclone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.pb.spotifyclone.models.MusicPlayer;
import pl.pb.spotifyclone.models.Track;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

        Track track = new Track(Files.readAllBytes(Paths.get("/home/bartek/Downloads/file_example_WAV_2MG.wav")), "wav");
        MusicPlayer.getInstance().setCurrentTrack(track);
        MusicPlayer.getInstance().start();
    }

    public static void main(String[] args) {
        launch();
    }
}

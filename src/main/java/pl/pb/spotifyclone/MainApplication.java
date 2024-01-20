package pl.pb.spotifyclone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.pb.spotifyclone.models.musicplayer.MusicService;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.models.track.TrackType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 800);
        stage.setTitle("SpotifyClone");
        stage.setScene(scene);

        ViewManager viewManager = ViewManager.getInstance();
        viewManager.setStage(stage);
        viewManager.switchView("home-view.fxml");

        Track track = Track.builder()
                .name("Rickroll")
                .albumName("Ricking Rolls")
                .authorName("Rick Astley")
                .bytes(Files.readAllBytes(Paths.get("C:\\Users\\Bartek\\Downloads\\Mzg1ODMxNTIzMzg1ODM3_JzthsfvUY24.MP3")))
                .fileType(TrackType.MP3)
                .build();
        //Track track = new Track("rickroll", Files.readAllBytes(Paths.get("C:\\Users\\Bartek\\Downloads\\Mzg1ODMxNTIzMzg1ODM3_JzthsfvUY24.MP3")), TrackType.MP3);
        MusicService.getInstance().setSingleTrack(track);
    }

    public static void main(String[] args) {
        launch();
    }
}

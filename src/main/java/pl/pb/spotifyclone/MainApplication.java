package pl.pb.spotifyclone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.pb.spotifyclone.models.musicplayer.MusicService;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.models.track.TrackType;
import pl.pb.spotifyclone.repositories.PlaylistRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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

        Playlist playlist = new Playlist("test", new ArrayList<>());

        Track track1 = Track.builder()
                .name("CHIPI CHIPI")
                .albumName("CHAPA CHAPA")
                .authorName("10h")
                .path("C:\\Users\\pawel\\Downloads\\CHIPI CHIPI.MP3")
                .fileType(TrackType.MP3)
                .build();

        Track track2 = Track.builder()
                .name("Rickroll")
                .albumName("Ricking Rolls")
                .authorName("Rick Astley")
                .path("C:\\Users\\pawel\\Downloads\\RickRoll.MP3")
                .fileType(TrackType.MP3)
                .build();

        playlist.getTracks().add(track1);
        playlist.getTracks().add(track2);
        MusicService.getInstance().setPlaylist(playlist);
        try {
            PlaylistRepository.getInstance().addPlaylist(playlist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

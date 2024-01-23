package pl.pb.spotifyclone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.pb.spotifyclone.models.musicplayer.MusicService;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.models.track.TrackType;
import pl.pb.spotifyclone.repositories.PlaylistRepository;
import pl.pb.spotifyclone.repositories.TrackRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

        Playlist playlist = new Playlist("test", new ArrayList<>());

        Track track1 = Track.builder()
                .name("CHIPI CHIPI")
                .albumName("CHAPA CHAPA")
                .authorName("10h")
                .path("C:\\Users\\Bartek\\Downloads\\file_example_WAV_5MG.wav")
                .fileType(TrackType.WAV)
                .build();

        Track track2 = Track.builder()
                .name("Rickroll")
                .albumName("Ricking Rolls")
                .authorName("Rick Astley")
                .path("C:\\Users\\Bartek\\Downloads\\Mzg1ODMxNTIzMzg1ODM3_JzthsfvUY24.MP3")
                .fileType(TrackType.MP3)
                .build();

        playlist.getTracks().add(track1);
        playlist.getTracks().add(track2);
        MusicService.getInstance().setPlaylist(playlist);
        try {
            TrackRepository.getInstance().addTrack(track1);
            TrackRepository.getInstance().addTrack(track2);
            PlaylistRepository.getInstance().addPlaylist(playlist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

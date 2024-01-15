package pl.pb.spotifyclone;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.pb.spotifyclone.models.MusicPlayer;
import pl.pb.spotifyclone.models.Track;
import pl.pb.spotifyclone.models.TrackType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        ViewManager viewManager = ViewManager.getInstance();
        viewManager.setStage(stage);
        viewManager.switchView("home-view.fxml");

        try {
          ArrayList<Track> tracks = new ArrayList<>();
          tracks.add(new Track(Files.readAllBytes(Paths.get("/home/bartek/Downloads/sample-12s.mp3")), TrackType.MP3));
          MusicPlayer.getInstance().setCurrentTrack(tracks.get(0));
          MusicPlayer.getInstance().start();
          //Thread.sleep(5000);
          //MusicPlayer.getInstance().pause();
          //Thread.sleep(2000);
          //MusicPlayer.getInstance().resume();
        } catch(Exception ex) {
          ex.printStackTrace();  
        }
  }

    public static void main(String[] args) {
        launch();
    }
}

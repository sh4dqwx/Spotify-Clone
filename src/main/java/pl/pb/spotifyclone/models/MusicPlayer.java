package pl.pb.spotifyclone.models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import pl.pb.spotifyclone.models.interfaces.ISubscriber;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {
    private static MusicPlayer instance;
    private Playlist currentPlaylist;
    private Track currentTrack;
    private TrackProgress currentTrackProgress;
    private MediaPlayer player;
    private final List<ISubscriber<TrackProgress>> subscribers;

    private MusicPlayer() {
        subscribers = new ArrayList<>();
    }

    public static MusicPlayer getInstance() {
        if(instance == null) instance = new MusicPlayer();
        return instance;
    }

    public void setCurrentTrack(Track track) {
        try {
            currentTrack = track;

            File tmpFile = File.createTempFile("tmp", currentTrack.getFileType().getValue());
            try(FileOutputStream fos = new FileOutputStream(tmpFile)) {
                fos.write(currentTrack.getBytes());
            }

            Media media = new Media(tmpFile.toURI().toString());
            player = new MediaPlayer(media);

            player.statusProperty().addListener((observableValue, status, newStatus) -> {
                if(newStatus == MediaPlayer.Status.READY) {
                    currentTrackProgress = new TrackProgress(0, (int)player.getTotalDuration().toSeconds());
                    notifySubscribers(currentTrackProgress);
                }
            });

            player.currentTimeProperty().addListener((observableValue) -> {
                int position = (int)player.getCurrentTime().toSeconds();
                if(currentTrackProgress.position() == position) return;
                currentTrackProgress = new TrackProgress(position, currentTrackProgress.length());
                notifySubscribers(currentTrackProgress);
            });
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void notifySubscribers(TrackProgress trackProgressInfo) {
        for(ISubscriber<TrackProgress> s : subscribers) {
            s.update(trackProgressInfo);
        }
    }

    public void subscribe(ISubscriber<TrackProgress> s) {
        subscribers.add(s);
    }

    public void play() {
        if(currentTrack != null) player.play();
    }

    public void pause() {
        player.pause();
    }
}

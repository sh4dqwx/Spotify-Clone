package pl.pb.spotifyclone.models.musicplayer;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.models.track.TrackProgress;
import pl.pb.spotifyclone.models.interfaces.IMusicPlayer;
import pl.pb.spotifyclone.models.interfaces.IPublisher;
import pl.pb.spotifyclone.models.interfaces.ISubscriber;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer implements IMusicPlayer, IPublisher<MusicPlayerInfo> {
    private final MediaPlayer player;
    private File musicFile;
    private boolean isPlaying;
    private TrackProgress currentTrackProgress;
    private final List<ISubscriber<MusicPlayerInfo>> subscribers = new ArrayList<>();

    private void notifySubscribers(MusicPlayerInfo musicPlayerInfo) {
        for(ISubscriber<MusicPlayerInfo> subscriber : subscribers) subscriber.update(musicPlayerInfo);
    }

    public MusicPlayer(Track track) {
        try {
            musicFile = File.createTempFile("tmp", track.getFileType().getValue());
            try(FileOutputStream fos = new FileOutputStream(musicFile)) {
                fos.write(track.getBytes());
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }

        Media media = new Media(musicFile.toURI().toString());
        player = new MediaPlayer(media);

        player.statusProperty().addListener((observableValue, status, newStatus) -> {
            if (newStatus == MediaPlayer.Status.READY) {
                currentTrackProgress = new TrackProgress(0, (int)player.getTotalDuration().toSeconds());
                notifySubscribers(new MusicPlayerInfo(
                        track.getName(),
                        track.getAuthorName(),
                        MusicPlayerStatus.READY,
                        currentTrackProgress
                ));
            }
            else if(newStatus == MediaPlayer.Status.PAUSED) {
                notifySubscribers(new MusicPlayerInfo(
                        track.getName(),
                        track.getAuthorName(),
                        MusicPlayerStatus.PAUSED,
                        currentTrackProgress
                ));
            }
        });

        player.currentTimeProperty().addListener((observable) -> {
            int position = (int)player.getCurrentTime().toSeconds();
            if(currentTrackProgress.position() == position) return;
            currentTrackProgress = new TrackProgress(position, currentTrackProgress.length());
            notifySubscribers(new MusicPlayerInfo(
                    track.getName(),
                    track.getAuthorName(),
                    MusicPlayerStatus.PLAYING,
                    currentTrackProgress
            ));
        });

        player.setOnEndOfMedia(() -> {
            notifySubscribers(new MusicPlayerInfo(
                    track.getName(),
                    track.getAuthorName(),
                    MusicPlayerStatus.FINISHED,
                    currentTrackProgress
            ));
        });
    }

    @Override
    public void start() {
        player.play();
    }

    @Override
    public void pause() {
        player.pause();
    }

    @Override
    public void stop() {
        player.stop();
    }

    @Override
    public void setVolume(double value) {
        player.setVolume(value / 100);
    }

    @Override
    public void setPosition(double value) {
        player.seek(new Duration(value * 1000));
    }

    @Override
    public void subscribe(ISubscriber<MusicPlayerInfo> subscriber) {
        subscribers.add(subscriber);
    }
}

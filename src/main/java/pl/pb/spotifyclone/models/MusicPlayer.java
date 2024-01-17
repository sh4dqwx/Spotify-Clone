package pl.pb.spotifyclone.models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import pl.pb.spotifyclone.models.interfaces.IMusicPlayer;
import pl.pb.spotifyclone.models.interfaces.IPublisher;
import pl.pb.spotifyclone.models.interfaces.ISubscriber;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer implements IMusicPlayer, IPublisher<TrackProgress> {
    private final MediaPlayer player;
    private File musicFile;
    private TrackProgress currentTrackProgress;
    private final List<ISubscriber<TrackProgress>> subscribers = new ArrayList<>();

    private void notifySubscribers(TrackProgress trackProgress) {
        for(ISubscriber<TrackProgress> subscriber : subscribers) subscriber.update(trackProgress);
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
    public void subscribe(ISubscriber<TrackProgress> subscriber) {
        subscribers.add(subscriber);
    }
}

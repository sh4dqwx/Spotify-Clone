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
import java.util.Objects;

public class MusicPlayer implements IMusicPlayer, IPublisher<MusicPlayerInfo> {
    private final MediaPlayer player;
    private File musicFile;
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
                currentTrackProgress = new TrackProgress(0, (int) player.getTotalDuration().toSeconds());
                notifySubscribers(new MusicPlayerInfo(MusicPlayerStatus.READY, currentTrackProgress));
            }
        });

        player.currentTimeProperty().addListener((observableValue) -> {
            int position = (int)player.getCurrentTime().toSeconds();
            if(currentTrackProgress.position() == position) return;
            currentTrackProgress = new TrackProgress(position, currentTrackProgress.length());
            notifySubscribers(new MusicPlayerInfo(MusicPlayerStatus.PLAYING, currentTrackProgress));
        });

        player.setOnEndOfMedia(() -> {
            notifySubscribers(new MusicPlayerInfo(MusicPlayerStatus.FINISHED, currentTrackProgress));
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
    public void subscribe(ISubscriber<MusicPlayerInfo> subscriber) {
        subscribers.add(subscriber);
    }
}

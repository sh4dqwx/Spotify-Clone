package pl.pb.spotifyclone.models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import pl.pb.spotifyclone.models.interfaces.IMusicPlayer;
import pl.pb.spotifyclone.models.interfaces.IMusicService;
import pl.pb.spotifyclone.models.interfaces.IPublisher;
import pl.pb.spotifyclone.models.interfaces.ISubscriber;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MusicService implements IMusicService, IPublisher<TrackProgress>, ISubscriber<TrackProgress> {
    private static MusicService instance;
    private Playlist currentPlaylist;
    private Track currentTrack;
    private IMusicPlayer player;
    private final List<ISubscriber<TrackProgress>> subscribers = new ArrayList<>();

    private MusicService() {}

    private void notifySubscribers(TrackProgress trackProgress) {
        for(ISubscriber<TrackProgress> s : subscribers) {
            s.update(trackProgress);
        }
    }

    public static MusicService getInstance() {
        if(instance == null) instance = new MusicService();
        return instance;
    }

    @Override
    public void setTrack(Track track) {
        currentPlaylist = null;
        currentTrack = track;
        player = new MusicPlayer(currentTrack);
    }

    @Override
    public void setPlaylist(Playlist playlist) {
        currentPlaylist = playlist;
        currentTrack = playlist.getTracks().get(0);
        player = new MusicPlayer(currentTrack);
    }

    @Override
    public void start() {
        if(currentTrack != null) player.start();
    }

    @Override
    public void pause() {
        player.pause();
    }

    @Override
    public void subscribe(ISubscriber<TrackProgress> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void update(TrackProgress object) {
        notifySubscribers(object);
    }
}

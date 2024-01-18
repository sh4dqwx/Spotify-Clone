package pl.pb.spotifyclone.models;

import pl.pb.spotifyclone.models.interfaces.IMusicPlayer;
import pl.pb.spotifyclone.models.interfaces.IMusicService;
import pl.pb.spotifyclone.models.interfaces.IPublisher;
import pl.pb.spotifyclone.models.interfaces.ISubscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MusicService implements IMusicService, IPublisher<TrackProgress>, ISubscriber<MusicPlayerInfo> {
    private static MusicService instance;
    private Playlist currentPlaylist;
    private PlaylistIterator currentPlaylistIterator;
    private Track currentTrack;
    private IMusicPlayer player;
    private final List<ISubscriber<TrackProgress>> subscribers = new ArrayList<>();

    private MusicService() {}

    private void notifySubscribers(TrackProgress trackProgress) {
        for(ISubscriber<TrackProgress> s : subscribers) {
            s.update(trackProgress);
        }
    }

    private void setPlayer() {
        player = new MusicPlayer(currentTrack);
        ((IPublisher<MusicPlayerInfo>)player).subscribe(this);
    }

    private void getNextTrack() {
        currentTrack = currentPlaylistIterator.next();
        setPlayer();
    }

    public static MusicService getInstance() {
        if(instance == null) instance = new MusicService();
        return instance;
    }

    @Override
    public void setSingleTrack(Track track) {
        currentPlaylist = new Playlist(track.getTitle(), new ArrayList<>(List.of(track)));
        setTrackOrder(PlaylistIteratorType.CLASSIC);
    }

    @Override
    public void setPlaylist(Playlist playlist) {
        currentPlaylist = playlist;
        setTrackOrder(PlaylistIteratorType.CLASSIC);
    }

    @Override
    public void setTrackOrder(PlaylistIteratorType type) {
        currentPlaylistIterator = currentPlaylist.iterator(type);
        getNextTrack();
    }

    @Override
    public void setLooped(boolean looped) {
        currentPlaylistIterator.setLooped(looped);
    }

    @Override
    public void start() {
        if(currentTrack == null) return;
        player.start();
    }

    @Override
    public void pause() {
        if(currentTrack == null) return;
        player.pause();
    }

    @Override
    public void subscribe(ISubscriber<TrackProgress> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void update(MusicPlayerInfo object) {
        System.out.println("{ " + object.status() + ", " + object.trackProgress().position() + ", " + object.trackProgress().length() + " }");
        notifySubscribers(object.trackProgress());
        if(object.status() != MusicPlayerStatus.FINISHED) return;
        if(!currentPlaylistIterator.hasNext()) return;
        getNextTrack();
        player.start();
    }
}

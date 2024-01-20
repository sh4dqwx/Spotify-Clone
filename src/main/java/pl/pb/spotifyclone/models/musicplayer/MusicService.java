package pl.pb.spotifyclone.models.musicplayer;

import pl.pb.spotifyclone.models.interfaces.IMusicPlayer;
import pl.pb.spotifyclone.models.interfaces.IMusicService;
import pl.pb.spotifyclone.models.interfaces.IPublisher;
import pl.pb.spotifyclone.models.interfaces.ISubscriber;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.models.playlist.PlaylistIterator;
import pl.pb.spotifyclone.models.playlist.PlaylistIteratorType;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.models.track.TrackProgress;

import java.util.ArrayList;
import java.util.List;

public class MusicService implements IMusicService, IPublisher<MusicPlayerInfo>, ISubscriber<MusicPlayerInfo> {
    private static MusicService instance;
    private Playlist currentPlaylist;
    private PlaylistIterator currentPlaylistIterator;
    private Track currentTrack;
    private IMusicPlayer player;
    private boolean isPlaying;
    private final List<ISubscriber<MusicPlayerInfo>> subscribers = new ArrayList<>();

    private MusicService() {}

    private void notifySubscribers(MusicPlayerInfo musicPlayerInfo) {
        for(ISubscriber<MusicPlayerInfo> s : subscribers) {
            s.update(musicPlayerInfo);
        }
    }

    private void setPlayer() {
        player = new MusicPlayer(currentTrack);
        ((IPublisher<MusicPlayerInfo>)player).subscribe(this);
    }

    public static MusicService getInstance() {
        if(instance == null) instance = new MusicService();
        return instance;
    }

    @Override
    public void setSingleTrack(Track track) {
        if(player != null) player.stop();
        currentPlaylist = new Playlist(track.getName(), new ArrayList<>(List.of(track)));
        setTrackOrder(PlaylistIteratorType.CLASSIC);
        if(isPlaying) player.start();
    }

    @Override
    public void setPlaylist(Playlist playlist) {
        if(player != null) player.stop();
        currentPlaylist = playlist;
        setTrackOrder(PlaylistIteratorType.CLASSIC);
        if(isPlaying) player.start();
    }

    @Override
    public void setTrackOrder(PlaylistIteratorType type) {
        if(player != null) player.stop();
        currentPlaylistIterator = currentPlaylist.iterator(type);
        nextTrack();
        if(isPlaying) player.start();
    }

    @Override
    public void setLooped(boolean looped) {
        currentPlaylistIterator.setLooped(looped);
    }

    @Override
    public void nextTrack() {
        if(player != null) player.stop();
        currentTrack = currentPlaylistIterator.next();
        setPlayer();
        if(isPlaying) player.start();
    }

    @Override
    public void prevTrack() {
        if(player != null) player.stop();
        currentTrack = currentPlaylistIterator.prev();
        setPlayer();
        if(isPlaying) player.start();
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
    public void stop() {
        if(currentTrack == null) return;
        player.stop();
    }

    @Override
    public void setVolume(double value) {
        player.setVolume(value);
    }

    @Override
    public void setPosition(double value) {
        player.setPosition(value);
    }

    @Override
    public void subscribe(ISubscriber<MusicPlayerInfo> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void update(MusicPlayerInfo object) {
        isPlaying = object.status() == MusicPlayerStatus.PLAYING;
        notifySubscribers(object);

        if(object.status() != MusicPlayerStatus.FINISHED) return;
        if(!currentPlaylistIterator.hasNext()) return;
        nextTrack();
        player.start();
    }
}

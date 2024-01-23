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
    private double volume;
    private IMusicPlayer player;
    private boolean isPlaying;
    private boolean isExplicitPermission;

    private final List<ISubscriber<MusicPlayerInfo>> subscribers = new ArrayList<>();

    private MusicService() {}

    private void setPlayer() {
        if(isExplicitPermission) player = new MusicPlayer(currentTrack);
        else player = new MusicPlayerProxy(new MusicPlayer(currentTrack));

        setVolume(volume);
        player.subscribe(this);
    }

    public static MusicService getInstance() {
        if(instance == null) instance = new MusicService();
        return instance;
    }

    @Override
    public Playlist getPlaylist() {
        return currentPlaylist;
    }


    @Override
    public void setSingleTrack(Track track) {
        if(player != null) player.pause();
        currentPlaylist = new Playlist(track.getName(), new ArrayList<>(List.of(track)));
        setTrackOrder(PlaylistIteratorType.CLASSIC);
        if(isPlaying) player.start();
    }

    @Override
    public void setPlaylist(Playlist playlist) {
        if(playlist.getTracks().isEmpty()) return;
        if(player != null) player.pause();
        currentPlaylist = playlist;
        setTrackOrder(PlaylistIteratorType.CLASSIC);
        if(isPlaying) player.start();
    }

    @Override
    public void clear() {
        if(player != null) player.pause();
        currentPlaylist = null;
        currentPlaylistIterator = null;
        currentTrack = null;
        player = null;

        notifySubscribers(new MusicPlayerInfo(
                "Wybierz utwór",
                "",
                MusicPlayerStatus.FINISHED,
                new TrackProgress(0, 0)
        ));
    }

    @Override
    public void setTrackOrder(PlaylistIteratorType type) {
        if(player != null) player.pause();
        currentPlaylistIterator = currentPlaylist.iterator(type);
        try {
            currentTrack = currentPlaylistIterator.next();
            setPlayer();
        } catch (Exception e) {
            if(isPlaying) player.start();
            return;
        }
        if(isPlaying) player.start();
    }

    @Override
    public void setLooped(boolean looped) {
        currentPlaylistIterator.setLooped(looped);
    }

    @Override
    public void setExplicitPermission(boolean explicitPermission) {
        if(player != null) player.pause();
        isExplicitPermission = explicitPermission;
        if(player != null) setPlayer();
        if(isPlaying) player.start();
    }

    @Override
    public void nextTrack() {
        if(player != null) player.pause();
        try {
            currentTrack = currentPlaylistIterator.next();
            if(!isExplicitPermission && currentTrack.getExplicit()) return;
        } catch (Exception ex) {
            if(isPlaying) player.start();
            return;
        }
        setPlayer();
        if(isPlaying) player.start();
    }

    @Override
    public void prevTrack() {
        if(!isExplicitPermission && getTrack().getExplicit()) return;
        if(player != null) player.pause();
        try {
            currentTrack = currentPlaylistIterator.prev();
        } catch (Exception ex) {
            if(isPlaying) player.start();
            return;
        }
        setPlayer();
        if(isPlaying) player.start();
    }

    @Override
    public Track getTrack() {
        return currentTrack;
    }

    @Override
    public void start() {
        if(player == null) return;
        player.start();
    }

    @Override
    public void pause() {
        if(player == null) return;
        player.pause();
    }

    @Override
    public void stop() {
        if(player == null) return;
        player.stop();
    }

    @Override
    public void setVolume(double value) {
        if(player == null) return;
        volume = value;
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
    public void notifySubscribers(MusicPlayerInfo musicPlayerInfo) {
        for(ISubscriber<MusicPlayerInfo> s : subscribers) {
            s.update(musicPlayerInfo);
        }
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

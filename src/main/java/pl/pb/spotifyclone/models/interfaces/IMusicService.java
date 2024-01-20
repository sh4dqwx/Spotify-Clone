package pl.pb.spotifyclone.models.interfaces;

import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.models.playlist.PlaylistIteratorType;
import pl.pb.spotifyclone.models.track.Track;

public interface IMusicService extends IMusicPlayer {
    void setSingleTrack(Track track);
    void setPlaylist(Playlist playlist);
    void setTrackOrder(PlaylistIteratorType type);
    void setLooped(boolean looped);
    void nextTrack();
    void prevTrack();
}

package pl.pb.spotifyclone.models.interfaces;

import pl.pb.spotifyclone.models.Playlist;
import pl.pb.spotifyclone.models.PlaylistIteratorType;
import pl.pb.spotifyclone.models.Track;
import pl.pb.spotifyclone.models.TrackProgress;

public interface IMusicService extends IMusicPlayer {
    void setSingleTrack(Track track);
    void setPlaylist(Playlist playlist);
    void setTrackOrder(PlaylistIteratorType type);
}

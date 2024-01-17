package pl.pb.spotifyclone.models.interfaces;

import pl.pb.spotifyclone.models.Playlist;
import pl.pb.spotifyclone.models.Track;
import pl.pb.spotifyclone.models.TrackProgress;

public interface IMusicService extends IMusicPlayer {
    void setTrack(Track track);
    void setPlaylist(Playlist playlist);
}

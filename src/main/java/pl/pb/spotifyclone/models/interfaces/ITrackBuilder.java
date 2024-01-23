package pl.pb.spotifyclone.models.interfaces;

import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.models.track.TrackType;

public interface ITrackBuilder {
    ITrackBuilder reset();
    ITrackBuilder addName(String name);
    ITrackBuilder addAlbumName(String name);
    ITrackBuilder addAuthorName(String name);
    ITrackBuilder addReleaseYear(int releaseYear);
    ITrackBuilder addPath(String path);
    ITrackBuilder addFiletype(TrackType filetype);
    ITrackBuilder addExplicit(boolean explicit);
    Track getResult();
}

package pl.pb.spotifyclone.models.playlist;

import pl.pb.spotifyclone.models.track.Track;

import java.util.List;

public abstract class PlaylistIterator {
    protected final List<Track> tracks;
    protected boolean looped = false;

    protected PlaylistIterator(List<Track> tracks) {
        this.tracks = tracks;
    }

    public abstract Track next();
    public abstract Track prev();

    public abstract boolean hasNext();
    public abstract boolean hasPrev();

    public void setLooped(boolean looped) {
        this.looped = looped;
    }
}

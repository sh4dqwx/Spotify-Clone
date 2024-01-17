package pl.pb.spotifyclone.models;

import pl.pb.spotifyclone.models.Track;

import java.util.List;

public abstract class PlaylistIterator {
    protected final List<Track> tracks;
    protected int currentTrack = 0;
    protected boolean looped = false;

    protected PlaylistIterator(List<Track> tracks) {
        this.tracks = tracks;
    }

    public abstract Track next();

    public abstract boolean hasNext();

    public void setLooped(boolean looped) {
        this.looped = looped;
    }
}

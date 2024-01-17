package pl.pb.spotifyclone.models;

import java.util.List;

public final class ClassicPlaylistIterator extends PlaylistIterator {
    public ClassicPlaylistIterator(List<Track> tracks) {
        super(tracks);
    }

    @Override
    public Track next() {
        Track track = tracks.get(currentTrack);
        currentTrack++;
        if (looped) currentTrack %= tracks.size();
        return track;
    }

    @Override
    public boolean hasNext() {
        return currentTrack == tracks.size();
    }
}
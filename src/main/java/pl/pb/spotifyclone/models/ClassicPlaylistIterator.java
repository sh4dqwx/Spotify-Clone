package pl.pb.spotifyclone.models;

import java.util.List;

public final class ClassicPlaylistIterator extends PlaylistIterator {
    private int currentTrackIndex = 0;
    public ClassicPlaylistIterator(List<Track> tracks) {
        super(tracks);
    }

    @Override
    public Track next() {
        Track track = tracks.get(currentTrackIndex);
        currentTrackIndex++;
        if (looped) currentTrackIndex %= tracks.size();
        return track;
    }

    @Override
    public boolean hasNext() {
        return currentTrackIndex < tracks.size();
    }
}
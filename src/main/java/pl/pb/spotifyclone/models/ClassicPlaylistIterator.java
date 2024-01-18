package pl.pb.spotifyclone.models;

import java.util.List;

public final class ClassicPlaylistIterator extends PlaylistIterator {
    private int index = 0;
    private int indexBeforeLooped = 0;

    public ClassicPlaylistIterator(List<Track> tracks) {
        super(tracks);
    }

    @Override
    public Track next() {
        Track track = tracks.get(index);
        index++;
        indexBeforeLooped = index;
        if (looped) index %= tracks.size();
        return track;
    }

    @Override
    public boolean hasNext() {
        return index < tracks.size();
    }

    @Override
    public void setLooped(boolean looped) {
        super.setLooped(looped);
        if(looped) index %= tracks.size();
        else index = indexBeforeLooped;
    }
}
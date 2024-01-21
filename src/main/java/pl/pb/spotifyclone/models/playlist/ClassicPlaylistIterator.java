package pl.pb.spotifyclone.models.playlist;

import pl.pb.spotifyclone.models.track.Track;

import java.util.List;

public final class ClassicPlaylistIterator extends PlaylistIterator {
    private int prevIndex = -2;
    private int currentIndex = -1;
    private int nextIndex = 0;

    public ClassicPlaylistIterator(List<Track> tracks) {
        super(tracks);
    }

    private void calculateIndexes() {
        prevIndex = currentIndex - 1;
        if(looped && currentIndex == tracks.size() - 1) nextIndex = 0;
        else nextIndex = currentIndex + 1;
    }

    @Override
    public Track next() throws Exception {
        if(!hasNext())
            throw new Exception("There is no next track <rzyg>");
        Track track = tracks.get(nextIndex);
        currentIndex = nextIndex;
        calculateIndexes();
        return track;
    }

    @Override
    public Track prev() throws Exception {
        if(!hasPrev())
            throw new Exception("There is no previous track");
        Track track = tracks.get(prevIndex);
        currentIndex = prevIndex;
        calculateIndexes();
        return track;
    }

    @Override
    public boolean hasNext() {
        return nextIndex < tracks.size();
    }

    @Override
    public boolean hasPrev() {
        return prevIndex >= 0;
    }

    @Override
    public void setLooped(boolean looped) {
        super.setLooped(looped);
        calculateIndexes();
    }
}
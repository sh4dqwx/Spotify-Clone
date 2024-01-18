package pl.pb.spotifyclone.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public final class RandomPlaylistIterator extends PlaylistIterator {
    private List<Integer> availableIndexes;
    private final Random random = new Random();

    private List<Integer> getAvailableIndexes(List<Track> tracks) {
        return new ArrayList<>(IntStream.range(0, tracks.size()).boxed().toList());
    }

    public RandomPlaylistIterator(List<Track> tracks) {
        super(tracks);
        availableIndexes = getAvailableIndexes(this.tracks);
    }

    @Override
    public Track next() {
        int randomIndex = random.nextInt(0, availableIndexes.size());
        Track track = tracks.get(availableIndexes.get(randomIndex));
        availableIndexes.remove(randomIndex);
        if(looped && !hasNext())
            availableIndexes = getAvailableIndexes(tracks);
        return track;
    }

    @Override
    public boolean hasNext() {
        return availableIndexes.size() > 0;
    }
}

package pl.pb.spotifyclone.repositories;

import lombok.Getter;
import pl.pb.spotifyclone.models.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrackRepository {
    @Getter
    private static final TrackRepository instance = new TrackRepository();
    private List<Track> trackList;

    private TrackRepository() {
        trackList = new ArrayList<>();
    }

    public void AddTrack(Track track) throws Exception {
        if(track == null)
            throw new Exception("Track can not be null");

        trackList.add(track);
    }

    public Track GetTrack(String title) {
        return trackList.stream()
                .filter(track -> track.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    public List<Track> GetTrackList() {
        return new ArrayList<>(trackList);
    }
}

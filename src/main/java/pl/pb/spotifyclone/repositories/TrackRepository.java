package pl.pb.spotifyclone.repositories;

import lombok.Getter;
import pl.pb.spotifyclone.models.track.Track;

import java.util.ArrayList;
import java.util.List;

public class TrackRepository {
    @Getter
    private static final TrackRepository instance = new TrackRepository();
    private List<Track> trackList;

    private TrackRepository() {
        trackList = new ArrayList<>();
    }

    public void addTrack(Track track) throws Exception {
        if(track == null)
            throw new Exception("Track can not be null");

        try {
            track.setId(trackList.getLast().getId() + 1);
        } catch (Exception e) {
            track.setId(0L);
        }
        trackList.add(track);
    }

    public void editTrack(Track track) throws Exception {
        if(track == null)
            throw new Exception("Track can not be null");

        trackList.replaceAll(existingTrack -> {
            if (existingTrack.getId().equals(track.getId()))
                return track;
            else
                return existingTrack;
        });
    }

    public Track getTrack(String title) {
        return trackList.stream()
                .filter(track -> track.getName().equals(title))
                .findFirst()
                .orElse(null);
    }

    public List<Track> getTrackContaining(String text) {
        if(text.isEmpty()) return trackList;
        return trackList.stream()
                .filter(track -> track.getName().contains(text))
                .toList();
    }

    public List<Track> getTrackList() {
        return new ArrayList<>(trackList);
    }
}

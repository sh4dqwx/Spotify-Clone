package pl.pb.spotifyclone.repositories;

import lombok.Getter;
import pl.pb.spotifyclone.models.interfaces.IPublisher;
import pl.pb.spotifyclone.models.interfaces.ISubscriber;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.models.track.TrackDirector;

import java.util.ArrayList;
import java.util.List;

public class TrackRepository implements IPublisher<List<Track>> {
    private static TrackRepository instance;
    private final PlaylistRepository playlistRepository;
    private List<Track> trackList;
    private List<ISubscriber<List<Track>>> subscribers;

    public static TrackRepository getInstance() {
        if(instance == null)
            instance = new TrackRepository();

        return instance;
    }

    private TrackRepository() {
        playlistRepository = PlaylistRepository.getInstance();
        trackList = new ArrayList<>();
        subscribers = new ArrayList<>();
    }

    public boolean contains(Track track) {
        return trackList.contains(track);
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
        notifySubscribers();
    }

    public void deleteTrack(Track track) throws Exception {
        if(track == null)
            throw new Exception("Track can not be null");

        for(Playlist playlist : playlistRepository.getPlaylistList()) {
            playlistRepository.removeTrack(playlist, track);
        }

        trackList.remove(track);
        notifySubscribers();
        playlistRepository.notifySubscribers();
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

    @Override
    public void subscribe(ISubscriber<List<Track>> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void notifySubscribers(List<Track> object) {
        for(ISubscriber<List<Track>> subscriber : subscribers) {
            subscriber.update(object);
        }
    }

    public void notifySubscribers() {
        notifySubscribers(trackList);
    }
}

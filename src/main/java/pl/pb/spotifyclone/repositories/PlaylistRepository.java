package pl.pb.spotifyclone.repositories;

import lombok.Getter;
import pl.pb.spotifyclone.models.interfaces.IPublisher;
import pl.pb.spotifyclone.models.interfaces.ISubscriber;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.models.track.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaylistRepository implements IPublisher<List<Playlist>> {
    private static PlaylistRepository instance;
    private List<Playlist> playlistList;
    private List<ISubscriber<List<Playlist>>> subscribers;

    public static PlaylistRepository getInstance() {
        if(instance == null)
            instance = new PlaylistRepository();

        return instance;
    }

    private PlaylistRepository() {
        playlistList = new ArrayList<>();
        subscribers = new ArrayList<>();
    }

    public void addPlaylist(Playlist playlist) throws Exception {
        if(playlist == null)
            throw new Exception("Playlist can not be null");
        try {
            playlist.setId(playlistList.getLast().getId() + 1);
        } catch (Exception e) {
            playlist.setId(0L);
        }
        playlistList.add(playlist);
        notifySubscribers();
    }

    public void editPlaylist(Playlist playlist) throws Exception {
        if(playlist == null)
            throw new Exception("Playlist can not be null");

        playlistList.replaceAll(existingPlaylist -> {
            if (existingPlaylist.getId().equals(playlist.getId()))
                return playlist;
            else
                return existingPlaylist;
        });
        notifySubscribers();
    }

    public void deletePlaylist(Playlist playlist) throws Exception {
        if(playlist == null)
            throw new Exception("Playlist can not be null");

        playlistList.remove(playlist);
        notifySubscribers();
    }

    public void removeTrack(Playlist playlist, Track track) throws Exception {
        Optional<Playlist> foundPlaylist = playlistList.stream()
                .filter(playlistToCompare -> playlistToCompare.getId().equals(playlist.getId()))
                .findFirst();

        if(foundPlaylist.isEmpty())
            throw new Exception("Problem");

        foundPlaylist.get().getTracks().remove(track);
        notifySubscribers();
    }

    public void addTrack(Playlist playlist, Track track) throws Exception {
        Optional<Playlist> foundPlaylist = playlistList.stream()
                .filter(playlistToCompare -> playlistToCompare.getId().equals(playlist.getId()))
                .findFirst();

        if(foundPlaylist.isEmpty())
            throw new Exception("Problem");

        foundPlaylist.get().getTracks().add(track);
        notifySubscribers();
    }

    public List<Playlist> getPlaylistList() {
        if(playlistList == null)
            return new ArrayList<>();
        else
            return new ArrayList<>(playlistList);
    }

    @Override
    public void subscribe(ISubscriber<List<Playlist>> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void notifySubscribers(List<Playlist> playlists) {
        for(ISubscriber<List<Playlist>> subscriber : subscribers)
            subscriber.update(playlists);
    }

    public void notifySubscribers() {
        notifySubscribers(playlistList);
    }
}

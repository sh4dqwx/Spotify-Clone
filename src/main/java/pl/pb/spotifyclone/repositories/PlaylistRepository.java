package pl.pb.spotifyclone.repositories;

import lombok.Getter;
import pl.pb.spotifyclone.models.interfaces.IPublisher;
import pl.pb.spotifyclone.models.interfaces.ISubscriber;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.models.track.Track;

import java.util.ArrayList;
import java.util.List;
public class PlaylistRepository implements IPublisher<List<Playlist>> {
    @Getter
    private static final PlaylistRepository instance = new PlaylistRepository();
    private List<Playlist> playlistList;
    private List<ISubscriber<List<Playlist>>> subscribers;

    private PlaylistRepository() {
        playlistList = new ArrayList<>();
        subscribers = new ArrayList<>();
    }

    private void notifySubscribers(List<Playlist> playlists) {
        for(ISubscriber<List<Playlist>> subscriber : subscribers)
            subscriber.update(playlists);
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
        notifySubscribers(playlistList);
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
        notifySubscribers(playlistList);
    }

    public Playlist getPlaylist(String title) {
        return playlistList.stream()
                .filter(playlist -> playlist.getTitle().equals(title))
                .findFirst()
                .orElse(null);
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
}

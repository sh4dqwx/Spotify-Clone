package pl.pb.spotifyclone.models.playlist;

import java.util.List;

import lombok.Data;
import lombok.NonNull;
import pl.pb.spotifyclone.models.track.Track;

@Data
public class Playlist {
    public Long id;
    @NonNull public String title;
    @NonNull public List<Track> tracks;

    public int getTracksCount() {
        return tracks.size();
    }

    public PlaylistIterator iterator(PlaylistIteratorType type) {
        PlaylistIterator iterator = null;
        switch(type) {
          case CLASSIC -> iterator = new ClassicPlaylistIterator(tracks);
          case RANDOM -> iterator = new RandomPlaylistIterator(tracks);
        }
        return iterator;
    }
}

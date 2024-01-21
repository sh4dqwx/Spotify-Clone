package pl.pb.spotifyclone.models.playlist;

import java.util.List;

import lombok.Data;
import lombok.NonNull;
import pl.pb.spotifyclone.models.track.Track;

@Data
public class Playlist {
    private Long id;
  @NonNull String title;
  @NonNull List<Track> tracks;

    public PlaylistIterator iterator(PlaylistIteratorType type) {
        PlaylistIterator iterator = null;
        switch(type) {
          case CLASSIC -> iterator = new ClassicPlaylistIterator(tracks);
          case RANDOM -> iterator = new RandomPlaylistIterator(tracks);
        }
        return iterator;
    }
}

package pl.pb.spotifyclone.models;

import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class Playlist {
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

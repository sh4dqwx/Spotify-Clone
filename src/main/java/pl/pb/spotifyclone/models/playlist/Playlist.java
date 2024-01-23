package pl.pb.spotifyclone.models.playlist;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.utils.Exporter;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Playlist {
    public Long id;
    @NonNull public String title;
    @NonNull public List<Track> tracks;

    @JsonIgnore
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

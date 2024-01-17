package pl.pb.spotifyclone.models;

import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class Playlist {
  @NonNull String title;
  @NonNull List<Track> tracks;
}

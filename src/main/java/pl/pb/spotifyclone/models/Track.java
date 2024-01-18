package pl.pb.spotifyclone.models;

import lombok.Data;
import lombok.NonNull;

@Data
public class Track {
  @NonNull private String title;
  @NonNull private byte[] bytes;
  @NonNull private TrackType fileType;
}

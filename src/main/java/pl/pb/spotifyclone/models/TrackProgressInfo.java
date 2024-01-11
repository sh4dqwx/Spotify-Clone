package pl.pb.spotifyclone.models;

import lombok.Data;
import lombok.NonNull;

@Data
public class TrackProgressInfo {
  @NonNull Integer currentPosition;
  @NonNull Integer length;
}

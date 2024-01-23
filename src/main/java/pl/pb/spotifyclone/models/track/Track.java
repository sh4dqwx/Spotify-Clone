package pl.pb.spotifyclone.models.track;

import lombok.*;

import java.nio.file.Path;


@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Track {
  public Long id;
  @NonNull public String name;
  public String albumName = "";
  public String authorName = "";
  public int releaseYear = 0;
  @NonNull public String path;
  @NonNull public TrackType fileType;
  public Boolean explicit = false;
}
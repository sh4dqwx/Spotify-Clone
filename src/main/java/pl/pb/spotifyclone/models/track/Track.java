package pl.pb.spotifyclone.models.track;

import lombok.*;

import java.nio.file.Path;


@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Track {
  public Long id;
  public String name;
  @Builder.Default public String albumName = "";
  @Builder.Default public String authorName = "";
  public int releaseYear;
  public String path;
  public TrackType fileType;
  @Builder.Default public  Boolean explicit = false;
}
package pl.pb.spotifyclone.models.track;

import lombok.*;


@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Track {
  private Long id;
  private String name;
  @Builder.Default private String albumName = "";
  @Builder.Default private String authorName = "";
  private int releaseYear;
  private byte[] bytes;
  private TrackType fileType;
  @Builder.Default private Boolean explicit = false;
}
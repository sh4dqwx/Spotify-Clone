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
  private String albumName;
  private String authorName;
  private int releaseYear;
  private byte[] bytes;
  private TrackType fileType;
}
package pl.pb.spotifyclone.models.track;

public enum TrackType {
  WAV(".wav"),
  MP3(".mp3");

  private final String value;

  TrackType(String value) {
    this.value = value;
  }

  public String getValue() { return value; }
}

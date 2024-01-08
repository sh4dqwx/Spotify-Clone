package pl.pb.spotifyclone.models.interfaces;

import pl.pb.spotifyclone.models.Track;

public interface IFilePlayer {
  public void start(Track track);
  public void pause();
  public void resume();
}

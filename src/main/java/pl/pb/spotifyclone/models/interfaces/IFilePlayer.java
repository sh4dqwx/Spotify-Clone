package pl.pb.spotifyclone.models.interfaces;

import pl.pb.spotifyclone.models.Subscriber;
import pl.pb.spotifyclone.models.Track;
import pl.pb.spotifyclone.models.TrackProgressInfo;

public interface IFilePlayer {
  public void start(Track track);
  public void pause();
  public void resume();
  public void subscribe(Subscriber<TrackProgressInfo> subscriber);
}

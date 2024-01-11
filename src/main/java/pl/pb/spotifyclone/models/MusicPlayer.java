package pl.pb.spotifyclone.models;

import java.util.function.Function;

import pl.pb.spotifyclone.models.interfaces.IFilePlayer;

public class MusicPlayer extends Provider<TrackProgressInfo> {
  private static MusicPlayer instance;
  private FilePlayerFactory filePlayerFactory;
  private Track currentTrack;
  private IFilePlayer currentFilePlayer;
  private Subscriber<TrackProgressInfo> subscriber;
  public Provider<TrackProgressInfo> provider;

  private MusicPlayer() {
    filePlayerFactory = FilePlayerFactory.getInstance();
    provider = new Provider<>();
    subscriber = new Subscriber<TrackProgressInfo>(trackProgress -> {
      System.out.println("{ " + trackProgress.getCurrentPosition() + ", " + trackProgress.getLength() + " }");
      provider.notifySubscribers(trackProgress);
      return null;
    });
  }

  public static MusicPlayer getInstance() {
    if(instance == null) instance = new MusicPlayer();
    return instance;
  }

  public void setCurrentTrack(Track track) { currentTrack = track; }

  public void start() {
    currentFilePlayer = filePlayerFactory.createWAVPlayer();
    currentFilePlayer.subscribe(subscriber);
    currentFilePlayer.start(currentTrack);
  }

  public void pause() {
    currentFilePlayer.pause();
  }

  public void resume() {
    currentFilePlayer.resume();
  }
}

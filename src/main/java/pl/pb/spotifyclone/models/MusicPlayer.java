package pl.pb.spotifyclone.models;

import pl.pb.spotifyclone.models.interfaces.IFilePlayer;

public class MusicPlayer {
  private static MusicPlayer instance;
  private FilePlayerFactory filePlayerFactory;
  private Track currentTrack;
  private IFilePlayer currentFilePlayer;

  private MusicPlayer() {
    filePlayerFactory = FilePlayerFactory.getInstance();
  }

  public static MusicPlayer getInstance() {
    if(instance == null) instance = new MusicPlayer();
    return instance;
  }

  public void setCurrentTrack(Track track) { currentTrack = track; }

  public void start() {
    currentFilePlayer = filePlayerFactory.createWAVPlayer();
    currentFilePlayer.start(currentTrack);
  }

  public void pause() {
    currentFilePlayer.pause();
  }

  public void resume() {
    currentFilePlayer.resume();
  }
}

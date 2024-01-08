package pl.pb.spotifyclone.models;

import pl.pb.spotifyclone.models.interfaces.IFilePlayer;

public class FilePlayerFactory {
  private static FilePlayerFactory instance;

  private FilePlayerFactory() {}

  public static FilePlayerFactory getInstance() {
    if(instance == null) instance = new FilePlayerFactory();
    return instance;
  }

  public IFilePlayer createWAVPlayer() {
    return new WAVPlayer();
  }

  public IFilePlayer createMP3Player() {
    return new MP3Player();
  }  
}

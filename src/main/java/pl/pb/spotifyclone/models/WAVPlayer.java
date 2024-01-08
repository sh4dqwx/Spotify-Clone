package pl.pb.spotifyclone.models;

import java.io.ByteArrayInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import pl.pb.spotifyclone.models.interfaces.IFilePlayer;

public class WAVPlayer implements IFilePlayer {
  private Clip clip;

  @Override
  public void start(Track track) {
    ByteArrayInputStream byteStream = new ByteArrayInputStream(track.getBytes());
    try {
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(byteStream);
      clip = AudioSystem.getClip();
      clip.open(audioStream);
      clip.start();
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void pause() {
    clip.stop();
  }

  @Override
  public void resume() {
    clip.start();
  }
}

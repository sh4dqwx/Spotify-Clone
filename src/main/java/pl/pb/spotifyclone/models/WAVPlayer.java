package pl.pb.spotifyclone.models;

import java.io.ByteArrayInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import pl.pb.spotifyclone.models.interfaces.IFilePlayer;

public class WAVPlayer implements IFilePlayer {
  private Clip clip;
  private AtomicBoolean listenerThreadRunning;

  private int currentPosition;
  private int length;
  public Provider<TrackProgressInfo> currentPositionProvider;

  public WAVPlayer() {
    listenerThreadRunning = new AtomicBoolean(false);
    currentPositionProvider = new Provider<>();

    try {
      clip = AudioSystem.getClip();
      clip.addLineListener(new LineListener() {
        @Override
        public void update(LineEvent e) {
          if(e.getType() == LineEvent.Type.START) { System.out.println("line start"); new Thread(() -> {
            listenerThreadRunning.set(true);
            while(true) {
              if(listenerThreadRunning.get() == false) continue;
              int position = (int)(Math.ceil(clip.getMicrosecondPosition() / 1_000_000.0));
              if(currentPosition != position) {
                currentPosition = position;
                currentPositionProvider.notifySubscribers(new TrackProgressInfo(position, length));
              }
            }
          }).start(); }
          else if(e.getType() == LineEvent.Type.STOP) { System.out.println("line stop"); listenerThreadRunning.set(false); }
        }
      });
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void start(Track track) {
    ByteArrayInputStream byteStream = new ByteArrayInputStream(track.getBytes());
    try {
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(byteStream);
      clip.open(audioStream);

      currentPosition = 0;
      length = (int)(Math.ceil(clip.getMicrosecondLength() / 1_000_000.0));

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

  @Override
  public void subscribe(Subscriber<TrackProgressInfo> subscriber) {
    currentPositionProvider.subscribe(subscriber);
  }
}

package pl.pb.spotifyclone.models;

import java.io.ByteArrayInputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import pl.pb.spotifyclone.models.interfaces.IFilePlayer;

public class MP3Player implements IFilePlayer {
  private AdvancedPlayer player;
  private AtomicBoolean listenerThreadRunning;

  private int currentPosition;
  private int length;
  public Provider<TrackProgressInfo> currentPositionProvider;

  public MP3Player() {
    listenerThreadRunning = new AtomicBoolean(false);
    currentPositionProvider = new Provider<>();
  }

  @Override
  public void start(Track track) {
    ByteArrayInputStream bais = new ByteArrayInputStream(track.getBytes());
    try {
      player = new AdvancedPlayer(bais);
      player.setPlayBackListener(new PlaybackListener() {
        @Override
        public void playbackStarted(PlaybackEvent e) { System.out.println("line start"); new Thread(() -> {
            listenerThreadRunning.set(true);
            while(true) {
              if(listenerThreadRunning.get() == false) continue;
              int position = (int)(Math.ceil(player.);
              if(currentPosition != position) {
                currentPosition = position;
                currentPositionProvider.notifySubscribers(new TrackProgressInfo(position, length));
              }
            }
          }).start();
        }

        @Override
        public void playbackFinished(PlaybackEvent e) {

        }
      });
      player.play();
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  
  @Override
  public void pause() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'pause'");
  }
  
  @Override
  public void resume() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'resume'");
  }
  
  @Override
  public void subscribe(Subscriber<TrackProgressInfo> subscriber) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'subscribe'");
  }
}

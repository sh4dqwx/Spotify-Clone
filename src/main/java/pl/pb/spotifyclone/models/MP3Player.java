package pl.pb.spotifyclone.models;

import java.io.ByteArrayInputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;
import pl.pb.spotifyclone.models.interfaces.IFilePlayer;

public class MP3Player implements IFilePlayer {
  private Player player;
  private int currentPosition;

  @Override
  public void start(Track track) {
    currentPosition = 0;
    ByteArrayInputStream bais = new ByteArrayInputStream(track.getBytes());

    Thread playerThread = new Thread(() -> {
      try {
        player = new Player(bais);
        player.play();
      } catch(Exception ex) {
        ex.printStackTrace();
      }
    });

    Thread listenerThread = new Thread(() -> {
      while(true) {
        try {
          while(player == null) { Thread.sleep(100); }
          if(player.isComplete()) break;
          int position = Math.floorDiv(player.getPosition(), 1000) * 1000;
          if(position != currentPosition) {
            currentPosition = position;
            System.out.println("Current position: " + position + " ms");
          }
        } catch(Exception ex) {
          ex.printStackTrace();
        }
      }
    });

    playerThread.start();
    listenerThread.start();
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

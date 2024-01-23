package pl.pb.spotifyclone.models.interfaces;

import pl.pb.spotifyclone.models.musicplayer.MusicPlayerInfo;
import pl.pb.spotifyclone.models.track.Track;

public interface IMusicPlayer extends IPublisher<MusicPlayerInfo> {
    Track getTrack();
    void start();
    void pause();
    void stop();
    void setVolume(double value);
    void setPosition(double position);
}

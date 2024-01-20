package pl.pb.spotifyclone.models.interfaces;

public interface IMusicPlayer {
    void start();
    void pause();
    void stop();
    void setVolume(double value);
    void setPosition(double position);
}

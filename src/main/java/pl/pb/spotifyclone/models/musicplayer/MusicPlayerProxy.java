package pl.pb.spotifyclone.models.musicplayer;

import pl.pb.spotifyclone.models.interfaces.IMusicPlayer;
import pl.pb.spotifyclone.models.interfaces.IPublisher;
import pl.pb.spotifyclone.models.interfaces.ISubscriber;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.models.track.TrackProgress;

public class MusicPlayerProxy implements IMusicPlayer, IPublisher<MusicPlayerInfo> {
    private IMusicPlayer player;

    public MusicPlayerProxy(IMusicPlayer player) {
        this.player = player;
    }

    @Override
    public Track getTrack() {
        return player.getTrack();
    }

    @Override
    public void start() {
        if(!getTrack().explicit) {
            player.start();
            return;
        }

        notifySubscribers(new MusicPlayerInfo(
                getTrack().getName(),
                getTrack().getAuthorName(),
                MusicPlayerStatus.FINISHED,
                new TrackProgress(0, 0)
        ));
    }

    @Override
    public void pause() {
        if(!getTrack().explicit) player.pause();
    }

    @Override
    public void stop() {
        if(!getTrack().explicit) player.stop();
    }

    @Override
    public void setVolume(double value) {
        if(!getTrack().explicit) player.setVolume(value);
    }

    @Override
    public void setPosition(double position) {
        if(!getTrack().explicit) player.setPosition(position);
    }

    @Override
    public void subscribe(ISubscriber<MusicPlayerInfo> subscriber) {
        player.subscribe(subscriber);
    }

    @Override
    public void notifySubscribers(MusicPlayerInfo object) {
        player.notifySubscribers(object);
    }
}

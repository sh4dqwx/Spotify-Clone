package pl.pb.spotifyclone.models.track;

import pl.pb.spotifyclone.models.interfaces.ITrackBuilder;

public class TrackDirector {
    private static TrackDirector instance;
    private ITrackBuilder builder;

    private TrackDirector() {
        builder = new TrackBuilder();
    }

    public static TrackDirector getInstance() {
        if(instance == null) instance = new TrackDirector();
        return instance;
    }

    public ITrackBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(ITrackBuilder builder) {
        this.builder = builder;
    }
}

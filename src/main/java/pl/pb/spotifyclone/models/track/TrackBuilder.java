package pl.pb.spotifyclone.models.track;

import pl.pb.spotifyclone.models.interfaces.ITrackBuilder;

public class TrackBuilder implements ITrackBuilder {
    private Track result;

    public TrackBuilder() {
        reset();
    }
    @Override
    public ITrackBuilder reset() {
        result = new Track();
        return this;
    }

    @Override
    public ITrackBuilder addName(String name) {
        result.setName(name);
        return this;
    }

    @Override
    public ITrackBuilder addAlbumName(String name) {
        result.setAlbumName(name);
        return this;
    }

    @Override
    public ITrackBuilder addAuthorName(String name) {
        result.setAuthorName(name);
        return this;
    }

    @Override
    public ITrackBuilder addReleaseYear(int releaseYear) {
        result.setReleaseYear(releaseYear);
        return this;
    }

    @Override
    public ITrackBuilder addPath(String path) {
        result.setPath(path);
        return this;
    }

    @Override
    public ITrackBuilder addFiletype(TrackType filetype) {
        result.setFileType(filetype);
        return this;
    }

    @Override
    public ITrackBuilder addExplicit(boolean explicit) {
        result.setExplicit(explicit);
        return this;
    }

    @Override
    public Track getResult() {
        return result;
    }
}

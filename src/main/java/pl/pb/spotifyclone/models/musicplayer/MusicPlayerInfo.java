package pl.pb.spotifyclone.models.musicplayer;

import pl.pb.spotifyclone.models.track.TrackProgress;

public record MusicPlayerInfo(
        String name,
        String authorName,
        MusicPlayerStatus status,
        TrackProgress trackProgress
) {}

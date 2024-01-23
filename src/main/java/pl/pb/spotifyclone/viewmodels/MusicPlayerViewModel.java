package pl.pb.spotifyclone.viewmodels;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import pl.pb.spotifyclone.models.musicplayer.MusicPlayerInfo;
import pl.pb.spotifyclone.models.musicplayer.MusicPlayerStatus;
import pl.pb.spotifyclone.models.musicplayer.MusicService;
import pl.pb.spotifyclone.models.playlist.PlaylistIteratorType;
import pl.pb.spotifyclone.models.track.TrackProgress;
import pl.pb.spotifyclone.models.interfaces.ISubscriber;

import javafx.fxml.FXML;
import javafx.scene.image.Image;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MusicPlayerViewModel implements ISubscriber<MusicPlayerInfo>, Initializable {
    private final MusicService musicService;
    private boolean isPlaying = false;
    private boolean isRandom = false;
    private boolean isLooped = false;
    private boolean isExplicitPermission = false;
    private final Image playWI;
    private final Image playGI;
    private final Image randomWI;
    private final Image randomGI;
    private final Image repeatWI;
    private final Image repeatGI;
    private final Image explicitRI;
    private final Image explicitGI;

    @FXML
    private ImageView playIV;
    @FXML
    private ImageView randomIV;
    @FXML
    private ImageView loopIV;
    @FXML
    private ImageView explicitIV;
    @FXML
    private Slider musicSlider;
    @FXML
    private Label positionL;
    @FXML
    private Label lengthL;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Label nameL;
    @FXML
    private Label authorL;

    private String getTimeString(int seconds) {
        int minutes = seconds / 60; seconds %= 60;
        String secondsStr;
        if(seconds < 10) secondsStr = "0" + seconds;
        else secondsStr = String.valueOf(seconds);
        return minutes + ":" + secondsStr;
    }

    public MusicPlayerViewModel() throws URISyntaxException {
        musicService = MusicService.getInstance();
        musicService.subscribe(this);

        playWI = new Image(Objects.requireNonNull(
                getClass().getResource("/pl/pb/spotifyclone/icons/play_white.png")).toURI().toString()
        );
        playGI = new Image(Objects.requireNonNull(
                getClass().getResource("/pl/pb/spotifyclone/icons/play_green.png")).toURI().toString()
        );
        randomWI = new Image(Objects.requireNonNull(
                getClass().getResource("/pl/pb/spotifyclone/icons/random_white.png")).toURI().toString()
        );
        randomGI = new Image(Objects.requireNonNull(
                getClass().getResource("/pl/pb/spotifyclone/icons/random_green.png")).toURI().toString()
        );
        repeatWI = new Image(Objects.requireNonNull(
                getClass().getResource("/pl/pb/spotifyclone/icons/repeat_white.png")).toURI().toString()
        );
        repeatGI = new Image(Objects.requireNonNull(
                getClass().getResource("/pl/pb/spotifyclone/icons/repeat_green.png")).toURI().toString()
        );
        explicitRI = new Image(Objects.requireNonNull(
                getClass().getResource("/pl/pb/spotifyclone/icons/explicit_red.png")).toURI().toString()
        );
        explicitGI = new Image(Objects.requireNonNull(
                getClass().getResource("/pl/pb/spotifyclone/icons/explicit_green.png")).toURI().toString()
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        musicSlider.setOnMouseReleased(mouseEvent -> {
            musicService.setPosition(musicSlider.getValue());
            positionL.setText(getTimeString((int)musicSlider.getValue()));
        });
        volumeSlider.valueProperty().addListener((observer, oldValue, newValue) -> {
            musicService.setVolume(newValue.doubleValue());
        });
    }

    public void start() {
        if(isPlaying) {
            musicService.pause();
            playIV.setImage(playWI);
            isPlaying = false;
        } else {
            musicService.setVolume(volumeSlider.getValue());
            musicService.start();
            playIV.setImage(playGI);
            isPlaying = true;
        }
    }

    public void nextTrack() {
        musicService.nextTrack();
    }

    public void prevTrack() {
        musicService.prevTrack();
    }

    public void setRandom() {
        if(isRandom) {
            musicService.setTrackOrder(PlaylistIteratorType.CLASSIC);
            randomIV.setImage(randomWI);
            isRandom = false;
        } else {
            musicService.setTrackOrder(PlaylistIteratorType.RANDOM);
            randomIV.setImage(randomGI);
            isRandom = true;
        }
    }

    public void setRepeat() {
        if(isLooped) {
            musicService.setLooped(false);
            loopIV.setImage(repeatWI);
            isLooped = false;
        } else {
            musicService.setLooped(true);
            loopIV.setImage(repeatGI);
            isLooped = true;
        }
    }

    public void changeExplicitPermission() {
        if(isExplicitPermission) {
            musicService.setExplicitPermission(false);
            explicitIV.setImage(explicitRI);
            isExplicitPermission = false;
        } else {
            musicService.setExplicitPermission(true);
            explicitIV.setImage(explicitGI);
            isExplicitPermission = true;
        }
    }

    @Override
    public void update(MusicPlayerInfo object) {
        System.out.println(object.status());

        musicSlider.setMin(0);
        musicSlider.setMax(object.trackProgress().length());
        musicSlider.setValue(object.trackProgress().position());

        nameL.setText(object.name());
        authorL.setText(object.authorName());
        lengthL.setText(getTimeString(object.trackProgress().length()));
        positionL.setText(getTimeString(object.trackProgress().position()));

        if(object.status() == MusicPlayerStatus.PLAYING) {
            playIV.setImage(playGI);
            isPlaying = true;
        } else {
            playIV.setImage(playWI);
            isPlaying = false;
        }
    }
}

package pl.pb.spotifyclone.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.pb.spotifyclone.ViewManager;
import pl.pb.spotifyclone.models.musicplayer.MusicService;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.repositories.TrackRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FilteredTracksViewModel implements Initializable{
    private ViewManager viewManager;
    private MusicService musicService;
    private TrackRepository trackRepository;
    private ObservableList<Track> observableTrackList;
    @FXML public TableView<Track> trackTableView;
    @FXML public TableColumn<Track,String> trackNameTableColumn;
    @FXML public TableColumn<Track,String> trackAlbumTableColumn;
    @FXML public TableColumn<Track,String> trackAuthorTableColumn;
    @FXML public TableColumn<Track,Integer> trackReleaseYearTableColumn;
    @FXML public TableColumn<Track,Boolean> trackExplicitTableColumn;

    @Setter private String keyWord;

    public FilteredTracksViewModel() {
        viewManager = ViewManager.getInstance();
        musicService = MusicService.getInstance();
        trackRepository = TrackRepository.getInstance();
        observableTrackList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        trackNameTableColumn.setCellValueFactory(new PropertyValueFactory<Track,String>("Name"));
        trackAlbumTableColumn.setCellValueFactory(new PropertyValueFactory<Track,String>("AlbumName"));
        trackAuthorTableColumn.setCellValueFactory(new PropertyValueFactory<Track,String>("AuthorName"));
        trackReleaseYearTableColumn.setCellValueFactory(new PropertyValueFactory<Track,Integer>("ReleaseYear"));
        trackExplicitTableColumn.setCellValueFactory(new PropertyValueFactory<Track,Boolean>("Explicit"));
        trackTableView.setPlaceholder(new Label("Brak utworów."));
        trackTableView.setItems(observableTrackList);
        trackTableView.getSelectionModel().selectedItemProperty().addListener((observer, oldValue, newValue) -> {
            musicService.setSingleTrack(newValue);
        });
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
        observableTrackList.clear();
        observableTrackList.addAll(trackRepository.getTrackContaining(this.keyWord));
    }

    public void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pl/pb/spotifyclone/home-view.fxml"));
            keyWord = "";
            viewManager.switchView(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

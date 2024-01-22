package pl.pb.spotifyclone.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.repositories.TrackRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class FilteredTracksViewModel implements Initializable{

    private TrackRepository trackRepository;
    private ObservableList<Track> observableTrackList;
    @FXML public TableView trackTableView;
    @FXML public TableColumn<Track,String> trackNameTableColumn;
    @FXML public TableColumn<Track,String> trackAlbumTableColumn;
    @FXML public TableColumn<Track,String> trackAuthorTableColumn;
    @FXML public TableColumn<Track,Integer> trackReleaseYearTableColumn;
    @FXML public TableColumn<Track,Boolean> trackExplicitTableColumn;

    @Setter private String keyWord;

    public FilteredTracksViewModel(){
        trackRepository = TrackRepository.getInstance();
        observableTrackList = FXCollections.observableArrayList(trackRepository.getTrackContaining(keyWord));
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
    }

}

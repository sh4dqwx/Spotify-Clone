package pl.pb.spotifyclone.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.repositories.PlaylistRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTrackToPlaylistViewModel implements Initializable {
    private Track track;
    private PlaylistRepository playlistRepository;
    @FXML private TableView<Playlist> playlistTableView;
    @FXML private TableColumn<Playlist,String> playlistNameColumn;
    @FXML private TableColumn<Playlist,Integer> playlistTrackCountColumn;
    private ObservableList<Playlist> playlistObservableList;

    public AddTrackToPlaylistViewModel() {
        playlistRepository = PlaylistRepository.getInstance();
        playlistObservableList = FXCollections.observableArrayList(playlistRepository.getPlaylistList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playlistNameColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        playlistTrackCountColumn.setCellValueFactory(new PropertyValueFactory<>("TracksCount"));
        playlistTableView.setPlaceholder(new Label("Brak playlist"));
        playlistTableView.setItems(playlistObservableList);
    }

    public void closeDialog() {
        Stage dialog = (Stage) playlistTableView.getScene().getWindow();
        dialog.close();
    }

    public void addClicked() {
        Playlist selectedPlaylist = playlistTableView.getSelectionModel().getSelectedItem();
        if (selectedPlaylist == null) {
            Alert playlistNull = new Alert(Alert.AlertType.ERROR);
            playlistNull.setTitle("Brak playlisty");
            playlistNull.setHeaderText("Nie wybrano playlisty");
            playlistNull.show();
            return;
        }

        try {
            playlistRepository.addTrack(selectedPlaylist, track);
            closeDialog();
            Alert trackAdded = new Alert(Alert.AlertType.INFORMATION);
            trackAdded.setTitle("Utwór dodany");
            trackAdded.setHeaderText("Pomyślnie dodano utwór do playlisty");
            trackAdded.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setTrack (Track track) {
        this.track = track;
    }
}

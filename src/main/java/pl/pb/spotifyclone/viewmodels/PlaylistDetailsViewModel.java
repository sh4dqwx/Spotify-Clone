package pl.pb.spotifyclone.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import pl.pb.spotifyclone.ViewManager;
import pl.pb.spotifyclone.models.interfaces.ISubscriber;
import pl.pb.spotifyclone.models.musicplayer.MusicService;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.repositories.PlaylistRepository;
import pl.pb.spotifyclone.repositories.TrackRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.net.URL;

public class PlaylistDetailsViewModel implements Initializable, ISubscriber<List<Playlist>> {
    private MusicService musicService;
    private TrackRepository trackRepository;
    private PlaylistRepository playlistRepository;
    private ViewManager viewManager;
    private ObservableList<Track> observableTrackList;
    private Playlist playlist;
    private Track selectedTrack;

    @FXML
    public TableView<Track> trackTableView;
    @FXML public TableColumn<Track,String> trackNameTableColumn;
    @FXML public TableColumn<Track,String> trackAlbumTableColumn;
    @FXML public TableColumn<Track,String> trackAuthorTableColumn;
    @FXML public TableColumn<Track,Integer> trackReleaseYearTableColumn;
    @FXML public TableColumn<Track,Boolean> trackExplicitTableColumn;
    @FXML public Label playlistName;

    public PlaylistDetailsViewModel() {
        musicService = MusicService.getInstance();
        trackRepository = TrackRepository.getInstance();
        playlistRepository = PlaylistRepository.getInstance();
        playlistRepository.subscribe(this);
        playlistRepository = PlaylistRepository.getInstance();
        viewManager = ViewManager.getInstance();
        observableTrackList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize (URL location, ResourceBundle resources){
        trackNameTableColumn.setCellValueFactory(new PropertyValueFactory<Track,String>("Name"));
        trackAlbumTableColumn.setCellValueFactory(new PropertyValueFactory<Track,String>("AlbumName"));
        trackAuthorTableColumn.setCellValueFactory(new PropertyValueFactory<Track,String>("AuthorName"));
        trackReleaseYearTableColumn.setCellValueFactory(new PropertyValueFactory<Track,Integer>("ReleaseYear"));
        trackExplicitTableColumn.setCellValueFactory(new PropertyValueFactory<Track,Boolean>("Explicit"));
        trackTableView.setPlaceholder(new Label("Brak utworów."));
        trackTableView.setItems(observableTrackList);
        trackTableView.getSelectionModel().selectedItemProperty().addListener((observer, oldValue, newValue) -> {
            selectedTrack = newValue;
            if(newValue == null) return;
            musicService.setSingleTrack(newValue);
        });
    }

    @SneakyThrows
    @Override
    public void update(List<Playlist> playlists) {
        Optional<Playlist> foundPlaylist = playlists.stream()
            .filter(playlistToCompare -> playlistToCompare.getId().equals(playlist.getId()))
            .findFirst();

        if(foundPlaylist.isEmpty())
            throw new Exception("Problem");

        playlist = foundPlaylist.get();
        observableTrackList.clear();
        observableTrackList.addAll(foundPlaylist.get().getTracks());
        playlistName.setText(foundPlaylist.get().getTitle());
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
        observableTrackList.clear();
        observableTrackList.addAll(playlist.getTracks());
        playlistName.setText(playlist.getTitle());
    }

    public void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pl/pb/spotifyclone/home-view.fxml"));
            viewManager.switchView(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEditPlaylistPopup()
    {
        try{
            URL url = getClass().getResource("/pl/pb/spotifyclone/edit-playlist-dialog.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            EditPlaylistViewModel viewModel = loader.getController();
            viewModel.setPlaylist(playlist);

            Stage secondStage = new Stage();
            secondStage.initModality(Modality.APPLICATION_MODAL);
            secondStage.setTitle("Edytuj playlistę");
            secondStage.setScene(new Scene(root));
            secondStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeTrackFromPlaylist() {
        try {
            playlistRepository.removeTrack(playlist, selectedTrack);
            trackRepository.notifySubscribers();
            Alert trackRemoved = new Alert(Alert.AlertType.INFORMATION);
            trackRemoved.setTitle("Utwór usunięty");
            trackRemoved.setHeaderText("Pomyślnie usunięto utwór z playlisty");
            trackRemoved.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

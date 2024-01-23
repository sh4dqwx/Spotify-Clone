package pl.pb.spotifyclone.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import pl.pb.spotifyclone.ViewManager;
import pl.pb.spotifyclone.models.interfaces.ISubscriber;
import pl.pb.spotifyclone.models.musicplayer.MusicService;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.repositories.PlaylistRepository;
import pl.pb.spotifyclone.repositories.TrackRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeViewModel implements ISubscriber<List<Playlist>>, Initializable {
    private final ViewManager viewManager;
    private final MusicService musicService;
    private final TrackRepository trackRepository;
    private PlaylistRepository playlistRepository;
    private Stage secondStage;
    private Playlist selectedPlaylist;

    @FXML private TextField sourceTextField;
    @FXML private TextField yearTextField;
    @FXML private TableView<Playlist> playlistTableView;
    @FXML private TableColumn<Playlist,String> playlistNameColumn;
    @FXML private TableColumn<Playlist,Integer> playlistTrackCountColumn;
    @FXML private TextField searchTextField;
    private URL url;
    private FXMLLoader loader;
    private Parent root;
    private String searchValue;
    private ObservableList<Playlist> observablePlaylistList;

    public HomeViewModel() {
        viewManager = ViewManager.getInstance();
        musicService = MusicService.getInstance();
        trackRepository = TrackRepository.getInstance();
        playlistRepository = PlaylistRepository.getInstance();
        playlistRepository.subscribe(this);
        observablePlaylistList = FXCollections.observableArrayList(playlistRepository.getPlaylistList());
    }

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        playlistNameColumn.setCellValueFactory(new PropertyValueFactory<Playlist,String>("Title"));
        playlistTrackCountColumn.setCellValueFactory(new PropertyValueFactory<Playlist,Integer>("TracksCount"));
        playlistTableView.setItems(observablePlaylistList);
        playlistTableView.setPlaceholder(new Label("Brak playlist."));
        playlistTableView.setOnMouseClicked(event -> {
            selectedPlaylist = playlistTableView.getSelectionModel().getSelectedItem();
            if(selectedPlaylist == null) return;
            if(event.getClickCount() == 2) {
                showPlaylistDetails(selectedPlaylist);
            } else if(event.getClickCount() == 1) {
                musicService.setPlaylist(selectedPlaylist);
            }
        });
        searchTextField.textProperty().addListener((observer, oldValue, newValue) -> {
            searchValue = newValue;
        });
    }

    public void showAddTrackPopup() {
        try{
            url = getClass().getResource("/pl/pb/spotifyclone/add-track-dialog.fxml");
            loader = new FXMLLoader(url);
            root = loader.load();

            secondStage = new Stage();
            secondStage.initModality(Modality.APPLICATION_MODAL);
            secondStage.setTitle("Dodaj utwór");
            secondStage.setScene(new Scene(root));
            secondStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showAddPlaylistPopup() {
        try {
            url = getClass().getResource("/pl/pb/spotifyclone/create-playlist-dialog.fxml");
            loader = new FXMLLoader(url);
            root = loader.load();

            secondStage = new Stage();
            secondStage.initModality(Modality.APPLICATION_MODAL);
            secondStage.setTitle("Utwórz playlistę");
            secondStage.setScene(new Scene(root));
            secondStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showImportPlaylistPopup() {
        try{
            url = getClass().getResource("/pl/pb/spotifyclone/import-playlist-dialog.fxml");
            loader = new FXMLLoader(url);
            root = loader.load();

            secondStage = new Stage();
            secondStage.initModality(Modality.APPLICATION_MODAL);
            secondStage.setTitle("Importuj playlistę");
            secondStage.setScene(new Scene(root));
            secondStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showExportPlaylistPopup() {
        try{
            url = getClass().getResource("/pl/pb/spotifyclone/export-playlist-dialog.fxml");
            loader = new FXMLLoader(url);
            root = loader.load();

            secondStage = new Stage();
            secondStage.initModality(Modality.APPLICATION_MODAL);
            secondStage.setTitle("Exportuj playlistę");
            secondStage.setScene(new Scene(root));
            secondStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void update(List<Playlist> playlists) {
        observablePlaylistList.clear();
        observablePlaylistList.addAll(playlists);
    }

    public void showPlaylistDetails(Playlist playlist) {
        try {
            url = getClass().getResource("/pl/pb/spotifyclone/playlist-details-view.fxml");
            loader = new FXMLLoader(url);
            root = loader.load();

            PlaylistDetailsViewModel viewModel = loader.getController();
            viewModel.setPlaylist(playlist);
            viewManager.switchView(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchClicked()
    {
        try {
            url = getClass().getResource("/pl/pb/spotifyclone/filtered-tracks-view.fxml");
            loader = new FXMLLoader(url);
            root = loader.load();

            FilteredTracksViewModel viewModel = loader.getController();
            viewModel.setKeyWord(searchValue == null ? "" : searchValue);
            viewManager.switchView(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePlaylistClicked()
    {
        if(selectedPlaylist == null) {
            Alert playlistNull = new Alert(Alert.AlertType.ERROR);
            playlistNull.setTitle("Brak playlisty");
            playlistNull.setHeaderText("Nie wybrano playlisty");
            playlistNull.show();
            return;
        }
        try {
            String playlistName = selectedPlaylist.getTitle();
            if(selectedPlaylist.equals(musicService.getPlaylist())) musicService.clear();
            playlistRepository.deletePlaylist(selectedPlaylist);
            Alert playlistDeleted = new Alert(Alert.AlertType.INFORMATION);
            playlistDeleted.setTitle("Playlista usunięta");
            playlistDeleted.setHeaderText("Pomyślnie usunięto playlistę " + playlistName);
            playlistDeleted.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
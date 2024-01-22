package pl.pb.spotifyclone.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import pl.pb.spotifyclone.ViewManager;
import pl.pb.spotifyclone.models.interfaces.ISubscriber;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.repositories.PlaylistRepository;
import pl.pb.spotifyclone.repositories.TrackRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeViewModel implements ISubscriber<List<Playlist>>, Initializable {
    private final TrackRepository trackRepository;
    private PlaylistRepository playlistRepository;
    private Stage secondStage;
    @FXML private TextField sourceTextField;
    @FXML private TextField yearTextField;
    @FXML private TableView<Playlist> playlistTableView;
    @FXML private TableColumn<Playlist,String> playlistNameColumn;
    @FXML private TableColumn<Playlist,Integer> playlistTrackCountColumn;
    @FXML private TextField searchTextField;
    private URL url;
    private FXMLLoader loader;
    private Parent root;
    private ObservableList<Playlist> observablePlaylistList;

    public HomeViewModel() {
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
    }

    @FXML
    private void handleNumberTextFieldKeyTyped(KeyEvent event) {
        String input = event.getCharacter();
        if (!input.matches("[0-9]")) {
            event.consume();
        }
    }

    public void addTrack(Track track) {
        try {
            trackRepository.addTrack(track);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void editTrack(Track track) {
        try {
            trackRepository.editTrack(track);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getTrack(String title)
    {
        try {
            trackRepository.getTrack(title);
        } catch (Exception e){
            e.printStackTrace();
        }
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

    public void searchClicked()
    {
        try{
            ViewManager viewManager = ViewManager.getInstance();
            viewManager.switchView("filtered-tracks-view.fxml");

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
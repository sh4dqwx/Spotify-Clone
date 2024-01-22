package pl.pb.spotifyclone.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import pl.pb.spotifyclone.models.interfaces.ISubscriber;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.repositories.PlaylistRepository;
import pl.pb.spotifyclone.utils.Exporter;
import pl.pb.spotifyclone.utils.FileFormat;

import java.net.URL;
import java.util.List;

import java.io.File;
import java.util.ResourceBundle;

public class ExportPlaylistViewModel implements Initializable {
    @FXML
    private TextField sourceTextField;
    private PlaylistRepository playlistRepository;
    @FXML private TableView<Playlist> playlistTableView;
    @FXML private TableColumn<Playlist,String> playlistNameColumn;
    @FXML private TableColumn<Playlist,Integer> playlistTrackCountColumn;
    @FXML private CheckBox xmlCheckBox;
    @FXML private CheckBox jsonCheckBox;
    private ObservableList<Playlist> observablePlaylistList;
    public ExportPlaylistViewModel() {
        playlistRepository = PlaylistRepository.getInstance();
        observablePlaylistList = FXCollections.observableArrayList(playlistRepository.getPlaylistList());
    }
    public void showPathChooserDialog()
    {
        DirectoryChooser dc = new DirectoryChooser();
        File selectedDir = dc.showDialog(null);
        if (selectedDir != null) {
            sourceTextField.setText(selectedDir.getAbsolutePath());
        }
    }

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        playlistNameColumn.setCellValueFactory(new PropertyValueFactory<Playlist,String>("Title"));
        playlistTrackCountColumn.setCellValueFactory(new PropertyValueFactory<Playlist,Integer>("TracksCount"));
        playlistTableView.setItems(observablePlaylistList);
        playlistTableView.setPlaceholder(new Label("Brak playlist."));
        jsonCheckBox.setSelected(true);
    }

    public void jsonClicked()
    {
        xmlCheckBox.setSelected(false);
        if(!jsonCheckBox.isSelected())
            jsonCheckBox.setSelected(true);
    }

    public void xmlClicked()
    {
        jsonCheckBox.setSelected(false);
        if(!xmlCheckBox.isSelected())
            xmlCheckBox.setSelected(true);
    }

    public void exportClicked() throws Exception {
        Playlist selectedPlaylist = playlistTableView.getSelectionModel().getSelectedItem();
        if(xmlCheckBox == null || jsonCheckBox == null)
            throw new Exception("Problem");

        if(jsonCheckBox.isSelected()) {
            Exporter.createExporter(FileFormat.JSON).exportPlaylist(
                    selectedPlaylist,
                    sourceTextField.getText() + "\\" + selectedPlaylist.getTitle()+".json"
            );
        } else {
            Exporter.createExporter(FileFormat.XML).exportPlaylist(
                    selectedPlaylist,
                    sourceTextField.getText() + "\\" + selectedPlaylist.getTitle()+".xml"
            );
        }
    }
}

package pl.pb.spotifyclone.viewmodels;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.repositories.PlaylistRepository;
import pl.pb.spotifyclone.repositories.TrackRepository;
import pl.pb.spotifyclone.utils.FileFormat;
import pl.pb.spotifyclone.utils.Importer;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Objects;

public class ImportPlaylistViewModel {
    @FXML private TextField sourceTextField;
    private PlaylistRepository playlistRepository;
    private TrackRepository trackRepository;

    public ImportPlaylistViewModel() {
        playlistRepository = PlaylistRepository.getInstance();
        trackRepository = TrackRepository.getInstance();
    }

    public void closeDialog() {
        Stage dialog = (Stage) sourceTextField.getScene().getWindow();
        dialog.close();
    }

    public void showFileChooserDialog()
    {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Pliki JSON lub XML", "*.JSON","*.xml"),
                new FileChooser.ExtensionFilter("Pliki JSON","*.JSON"),
                new FileChooser.ExtensionFilter("Pliki XML", "*.xml"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            sourceTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    public void importClicked() {
        String sourcePath = sourceTextField.getText();
        if (Objects.equals(sourcePath, ""))
        {
            Alert playlistNull = new Alert(Alert.AlertType.ERROR);
            playlistNull.setTitle("Brak ścieżki");
            playlistNull.setHeaderText("Nie podano ścieżki");
            playlistNull.show();
        }
        else
        {
        String extension = FilenameUtils.getExtension(sourcePath);
        Importer.ImporterPrototype importer = new Importer().getPrototype(extension.equals("json") ? FileFormat.JSON : FileFormat.XML);
        Playlist playlist = importer.importFile(sourcePath);

            try {
                playlistRepository.addPlaylist(playlist);
                for(Track track : playlist.getTracks()) {
                    if(trackRepository.contains(track)) continue;
                    trackRepository.addTrack(track);
                }
                closeDialog();

                Alert playlistExported = new Alert(Alert.AlertType.INFORMATION);
                playlistExported.setTitle("Playlista została zaimportowana");
                playlistExported.setHeaderText("Pomyślnie zaimportowano playlistę");
                playlistExported.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

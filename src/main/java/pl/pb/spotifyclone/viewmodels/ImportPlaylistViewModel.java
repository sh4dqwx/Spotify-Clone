package pl.pb.spotifyclone.viewmodels;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.commons.io.FilenameUtils;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.repositories.PlaylistRepository;
import pl.pb.spotifyclone.utils.FileFormat;
import pl.pb.spotifyclone.utils.Importer;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Objects;

public class ImportPlaylistViewModel {
    @FXML private TextField sourceTextField;
    private PlaylistRepository playlistRepository;

    public ImportPlaylistViewModel() {
        playlistRepository = PlaylistRepository.getInstance();
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
        String extension = FilenameUtils.getExtension(sourcePath);
        Importer.ImporterPrototype importer = new Importer().getPrototype(extension.equals("json") ? FileFormat.JSON : FileFormat.XML);
        Playlist playlist = importer.importFile(sourcePath);
        try {
            playlistRepository.addPlaylist(playlist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

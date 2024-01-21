package pl.pb.spotifyclone.viewmodels;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import pl.pb.spotifyclone.repositories.PlaylistRepository;

import java.io.File;

public class ExportPlaylistViewModel {
    @FXML
    private TextField sourceTextField;
    private PlaylistRepository playlistRepository;

    public ExportPlaylistViewModel() {
        playlistRepository = PlaylistRepository.getInstance();
    }
    public void showPathChooserDialog()
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
}

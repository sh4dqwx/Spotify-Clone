package pl.pb.spotifyclone.viewmodels;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class ImportPlaylistViewModel {
    @FXML private TextField playlistNameTextField;
    @FXML private TextField sourceTextField;
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
}

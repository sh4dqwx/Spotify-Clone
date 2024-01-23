package pl.pb.spotifyclone.viewmodels;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import pl.pb.spotifyclone.models.interfaces.ITrackBuilder;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.models.track.TrackDirector;
import pl.pb.spotifyclone.models.track.TrackType;
import pl.pb.spotifyclone.repositories.TrackRepository;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class EditTrackViewModel implements Initializable {
    private final TrackRepository trackRepository;
    private final TrackDirector trackDirector;
    private Track track;
    @FXML private TextField nameTextField;
    @FXML private TextField albumTextField;
    @FXML private TextField authorTextField;
    @FXML private TextField sourceTextField;
    @FXML private TextField yearTextField;
    @FXML private CheckBox explicitCheckBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        yearTextField.setOnKeyTyped(this::handleNumberTextFieldKeyTyped);
    }

    public EditTrackViewModel(){
        trackRepository = TrackRepository.getInstance();
        trackDirector = TrackDirector.getInstance();
    }

    public void closeDialog() {
        Stage dialog = (Stage) nameTextField.getScene().getWindow();
        dialog.close();
    }

    @FXML
    private void handleNumberTextFieldKeyTyped(KeyEvent event){
        String input = event.getText();
        if (!input.matches("[0-9]")) {
            yearTextField.setText(yearTextField.getText().replaceAll("[^0-9]", ""));
            yearTextField.positionCaret(yearTextField.getText().length());
        }
    }

    public void editTrack() {
        if (nameTextField.getText().isEmpty()) {
            Alert emptyTrackName = new Alert(Alert.AlertType.ERROR);
            emptyTrackName.setTitle("Nazwa utworu pusta");
            emptyTrackName.setHeaderText("Nie podano nazwy utworu");
            emptyTrackName.show();
            return;
        }

        try {
            Path path = Paths.get(sourceTextField.getText());
            if (sourceTextField.getText().isEmpty() || !Files.exists(path)) {
                Alert wrongPath = new Alert(Alert.AlertType.ERROR);
                wrongPath.setTitle("Ścieżka nie istnieje");
                wrongPath.setHeaderText("Podana ścieżka nie istnieje");
                wrongPath.show();
                return;
            }

            String trackType = sourceTextField.getText().substring(sourceTextField.getText().length() - 3).toLowerCase();
            if(trackType.isEmpty()) {
                Alert emptyTrackType = new Alert(Alert.AlertType.ERROR);
                emptyTrackType.setTitle("Typ utworu pusty");
                emptyTrackType.setHeaderText("Nie podano typu utworu");
                emptyTrackType.show();
                return;
            }
            if(!trackType.equals("wav") && !trackType.equals("mp3")) {
                Alert wrongTrackType = new Alert(Alert.AlertType.ERROR);
                wrongTrackType.setTitle("Typ utworu błędny");
                wrongTrackType.setHeaderText("Podano błędny typ utworu");
                wrongTrackType.show();
                return;
            }

            track.setName(nameTextField.getText());
            track.setAlbumName(albumTextField.getText());
            track.setAuthorName(authorTextField.getText());
            track.setReleaseYear(Integer.parseInt(yearTextField.getText()));
            track.setFileType(trackType.equals("wav") ? TrackType.WAV : TrackType.MP3);
            track.setExplicit(explicitCheckBox.isSelected());

            trackRepository.editTrack(track);
            closeDialog();

            Alert trackAdded = new Alert(Alert.AlertType.INFORMATION);
            trackAdded.setTitle("Utwór został edytowany");
            trackAdded.setHeaderText("Zedytowano utwór: " + nameTextField.getText());
            trackAdded.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showFileChooserDialog()
    {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Pliki MP3 lub WAV", "*.mp3","*.wav"),
                new FileChooser.ExtensionFilter("Pliki MP3","*.mp3"),
                new FileChooser.ExtensionFilter("Pliki WAV", "*.wav"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            sourceTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    public void setTrack (Track track) {
        this.track = track;
        nameTextField.setText(track.getName());
        albumTextField.setText(track.getAlbumName());
        authorTextField.setText(track.getAuthorName());
        yearTextField.setText(String.valueOf(track.getReleaseYear()));
        sourceTextField.setText(track.getPath());
        explicitCheckBox.setSelected(track.getExplicit());
    }
}

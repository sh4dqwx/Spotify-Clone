package pl.pb.spotifyclone.viewmodels;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import org.w3c.dom.Text;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.models.track.TrackType;
import pl.pb.spotifyclone.repositories.TrackRepository;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class AddTrackViewModel implements Initializable {
    private final TrackRepository trackRepository;
    @FXML private TextField nameTextField;
    @FXML private TextField albumTextField;
    @FXML private TextField authorTextField;
    @FXML private TextField sourceTextField;
    @FXML private TextField yearTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        yearTextField.setOnKeyTyped(this::handleNumberTextFieldKeyTyped);
    }

    public AddTrackViewModel(){
        trackRepository = TrackRepository.getInstance();
    }

    @FXML
    private void handleNumberTextFieldKeyTyped(KeyEvent event){
        String input = event.getText();
        if (!input.matches("[0-9]")) {
            yearTextField.setText(yearTextField.getText().replaceAll("[^0-9]", ""));
            yearTextField.positionCaret(yearTextField.getText().length());
        }
    }

    public void addTrack() {
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

            byte[] pathInBytes = Files.readAllBytes(path);
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

            Track.TrackBuilder newTrack = Track.builder()
                    .bytes(pathInBytes);
            newTrack.fileType(trackType.equals("wav") ? TrackType.WAV : TrackType.MP3);
            if(!nameTextField.getText().isEmpty()) newTrack.name(nameTextField.getText());
            if(!authorTextField.getText().isEmpty()) newTrack.authorName(authorTextField.getText());
            if(!albumTextField.getText().isEmpty()) newTrack.albumName(albumTextField.getText());
            trackRepository.addTrack(newTrack.build());
            Alert trackAdded = new Alert(Alert.AlertType.INFORMATION);
            trackAdded.setTitle("Utwór dodany");
            trackAdded.setHeaderText("Dodano utwór: " + nameTextField.getText());
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

}

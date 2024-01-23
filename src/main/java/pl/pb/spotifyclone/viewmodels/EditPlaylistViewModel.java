package pl.pb.spotifyclone.viewmodels;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.repositories.PlaylistRepository;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditPlaylistViewModel {
    private final PlaylistRepository playlistRepository;
    private Playlist playlist;
    @FXML private TextField nameTextField;

    public EditPlaylistViewModel(){
        playlistRepository = PlaylistRepository.getInstance();
    }

    public void closeDialog() {
        Stage dialog = (Stage) nameTextField.getScene().getWindow();
        dialog.close();
    }

    public void editPlaylist()
    {
        if (!Objects.equals(nameTextField.getText(), "")){
            try{
                playlist.setTitle(nameTextField.getText());
                playlistRepository.editPlaylist(playlist);
                closeDialog();

                Alert playlistEdited = new Alert(Alert.AlertType.INFORMATION);
                playlistEdited.setTitle("Playlista zedytowana");
                playlistEdited.setHeaderText("Zedytowano playlistę");
                playlistEdited.show();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            Alert wrongName = new Alert(Alert.AlertType.ERROR);
            wrongName.setTitle("Zła nazwa");
            wrongName.setHeaderText("Nie podano nazwy playlisty!");
            wrongName.show();
        }
    }

    public void setPlaylist(Playlist playlist){
        this.playlist=playlist;
        nameTextField.setText(playlist.getTitle());
    }
}

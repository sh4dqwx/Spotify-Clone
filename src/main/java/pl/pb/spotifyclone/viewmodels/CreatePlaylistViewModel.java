package pl.pb.spotifyclone.viewmodels;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.repositories.PlaylistRepository;

import java.util.ArrayList;
import java.util.Objects;

public class CreatePlaylistViewModel {
    private final PlaylistRepository playlistRepository;
    @FXML private TextField nameTextField;

    public CreatePlaylistViewModel(){
        playlistRepository = PlaylistRepository.getInstance();
    }

    public void closeDialog() {
        Stage dialog = (Stage) nameTextField.getScene().getWindow();
        dialog.close();
    }

    public void addPlaylist()
    {
        if (!Objects.equals(nameTextField.getText(), "")){
            try{
                Playlist newPlaylist = new Playlist(nameTextField.getText(),new ArrayList<>());
                playlistRepository.addPlaylist(newPlaylist);
                closeDialog();

                Alert playlistAdded = new Alert(Alert.AlertType.INFORMATION);
                playlistAdded.setTitle("Playlista dodana");
                playlistAdded.setHeaderText("Dodano playlistę");
                playlistAdded.show();

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
}

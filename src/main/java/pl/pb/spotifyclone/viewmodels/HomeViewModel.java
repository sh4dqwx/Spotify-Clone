package pl.pb.spotifyclone.viewmodels;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import pl.pb.spotifyclone.models.track.Track;
import pl.pb.spotifyclone.repositories.TrackRepository;

import java.io.IOException;
import java.net.URL;

public class HomeViewModel {
    private final TrackRepository trackRepository;
    private Stage secondStage;
    @FXML private TextField sourceTextField;
    @FXML private TextField yearTextField;
    private URL url;
    private FXMLLoader loader;
    private Parent root;

    public HomeViewModel() {
        trackRepository = TrackRepository.getInstance();
    }

    @FXML
    private void handleNumberTextFieldKeyTyped(KeyEvent event){
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

    public void showAddTrackPopup(){

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
    public void showImportPlaylistPopup(){

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


}
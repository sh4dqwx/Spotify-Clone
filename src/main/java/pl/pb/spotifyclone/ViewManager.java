package pl.pb.spotifyclone;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ViewManager {
    private static ViewManager instance;
    private Stage stage;

    private ViewManager() {}

    public static synchronized ViewManager getInstance() {
        if (instance == null) {
            instance = new ViewManager();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void switchView(String fxmlFile) throws IOException {
        if(stage == null) throw new IOException("ViewManager has no access to stage. Use setStage method before.");

        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
        GridPane mainGrid = (GridPane)stage.getScene().getRoot();
        GridPane.setHgrow(view, Priority.ALWAYS);
        GridPane.setVgrow(view, Priority.ALWAYS);

       if(mainGrid.getChildren().size() >= 2)
            mainGrid.getChildren().removeFirst();
        mainGrid.add(view, 0, 0);

        stage.show();
    }
}

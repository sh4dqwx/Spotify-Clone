module pl.pb.spotifyclone {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires static lombok;

    opens pl.pb.spotifyclone to javafx.fxml;
    exports pl.pb.spotifyclone;
    exports pl.pb.spotifyclone.viewmodels;
    opens pl.pb.spotifyclone.viewmodels to javafx.fxml;
}

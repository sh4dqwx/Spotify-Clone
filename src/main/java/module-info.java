module pl.pb.spotifyclone {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.swing;
    requires java.desktop;
    requires com.google.gson;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.xml;
    requires org.apache.commons.io;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires static lombok;

    opens pl.pb.spotifyclone to javafx.fxml;
    exports pl.pb.spotifyclone;
    exports pl.pb.spotifyclone.viewmodels;
    opens pl.pb.spotifyclone.viewmodels to javafx.fxml;
    exports pl.pb.spotifyclone.models.musicplayer;
    opens pl.pb.spotifyclone.models.musicplayer to javafx.fxml;
    exports pl.pb.spotifyclone.models.track;
    opens pl.pb.spotifyclone.models.track to javafx.fxml;
    exports pl.pb.spotifyclone.models.playlist;
    opens pl.pb.spotifyclone.models.playlist to javafx.fxml;
}

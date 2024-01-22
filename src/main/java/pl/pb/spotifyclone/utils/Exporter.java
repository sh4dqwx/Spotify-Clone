package pl.pb.spotifyclone.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.utils.FileFormat;

import java.io.FileWriter;
import java.io.IOException;

import static pl.pb.spotifyclone.utils.FileFormat.JSON;
import static pl.pb.spotifyclone.utils.FileFormat.XML;

public class Exporter {
    public static PlaylistExporter createExporter(FileFormat format) {
        switch (format) {
            case XML -> { return new XMLPlaylistExporter(); }
            case JSON -> { return new JSONPlaylistExporter(); }
            default -> throw new IllegalArgumentException("Unsupported format");
        }
    }

    private static class XMLPlaylistExporter implements PlaylistExporter {
        @Override
        public void exportPlaylist(Playlist playlist, String outputPath) {
            XmlMapper xmlMapper = new XmlMapper();
            try {
                String xml = xmlMapper.writeValueAsString(playlist);
                try(FileWriter writer = new FileWriter(outputPath)) {
                    writer.write(xml);
                    writer.flush();
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class JSONPlaylistExporter implements PlaylistExporter {
        @Override
        public void exportPlaylist(Playlist playlist, String outputPath) {
            Gson gson = new Gson();
            try(FileWriter writer = new FileWriter(outputPath)) {
                String json = gson.toJson(playlist);
                writer.write(json);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface PlaylistExporter {
        void exportPlaylist(Playlist playlist, String outputPath) throws JsonProcessingException;
    }
}

package pl.pb.spotifyclone.utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import pl.pb.spotifyclone.models.playlist.Playlist;
import pl.pb.spotifyclone.utils.FileFormat;

import java.io.FileReader;

public class Importer {
    private final JSONPlaylistImporter jsonPlaylistImporter;
    private final XMLPlaylistImporter xmlPlaylistImporter;

    public Importer() {
        jsonPlaylistImporter = new JSONPlaylistImporter();
        xmlPlaylistImporter = new XMLPlaylistImporter();
    }
    public ImporterPrototype getPrototype(FileFormat format) {
        switch (format) {
            case XML -> { return jsonPlaylistImporter.clone(); }
            case JSON -> { return xmlPlaylistImporter.clone(); }
            default -> throw new IllegalArgumentException("Unsupported format");
        }
    }

    private static class JSONPlaylistImporter implements ImporterPrototype {
        @Override
        public ImporterPrototype clone() {
            return new JSONPlaylistImporter();
        }

        @Override
        public Playlist importFile(String inputPath) {
            Gson gson = new Gson();
            try(FileReader reader = new FileReader(inputPath)) {
                return gson.fromJson(reader, Playlist.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private static class XMLPlaylistImporter implements ImporterPrototype {
        @Override
        public ImporterPrototype clone() {
            return new XMLPlaylistImporter();
        }

        @Override
        public Playlist importFile(String inputPath) {
            XmlMapper xmlMapper = new XmlMapper();
            try(FileReader reader = new FileReader(inputPath)) {
                return xmlMapper.readValue(reader, Playlist.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public interface ImporterPrototype {
        ImporterPrototype clone();
        Playlist importFile(String inputPath);
    }
}

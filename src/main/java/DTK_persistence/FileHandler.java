package DTK_persistence;

import DTK_model.Game;
import DTK_model.Map;
import DTK_model.Pair;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.*;

/**
 * Implementation of FileHandler class, contains static methods for saving and loading Map and Game state to and from files.
 */
public class FileHandler {

    /**
     * Jackson objectMapper for serializing data.
     */
    static final ObjectMapper mapper = new ObjectMapper().registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));


    /**
     * Saves a Map instance to a specified file.
     *
     * @param file the File we want to save to
     * @param map  the Map instance we want to save
     */
    public static void saveMap(File file, Map map) {
        saveToJson(file, map, ".dtk");
    }

    /**
     * Saves a Game instance to a specified file.
     *
     * @param file the File we want to save to
     * @param game the Game instance we want to save
     */
    public static void saveGame(File file, Game game) {
        game.getDatabase().closeConnection();
        saveToStream(file, game, ".dtk_save");
        game.getDatabase().openConnection();
    }

    /**
     * The logic of saving an Object state to a specified file with a specified file type.
     *
     * @param file the File we want to save to
     * @param obj  the Object we want to save
     * @param type the extension of the file we save to
     */
    public static void saveToJson(File file, Object obj, String type) {
        try {
            String fileName = file.getName().split("\\.")[0] + type;
            FileOutputStream fileOut = new FileOutputStream(fileName);
            serialize(fileOut, obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a Map instance from a specified file.
     *
     * @param file the file we want to load from
     * @return a Map instance from the specified file
     */
    public static Map loadMap(File file) {
        try {
            return mapper.readValue(file, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Loads a Map and a File from a specified file.
     *
     * @param file the file we want to load from
     * @return a Pair of Map and File instances from the specified file
     */
    public static Pair<Map, File> loadMapAndFile(File file) {
        Map map = null;
        try {
            map = mapper.readValue(file, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Pair<>(map, file);
    }

    /**
     * Loads a Game instance from a specified file.
     *
     * @param file the file we want to load from
     * @return a Game instance from the specified file
     */
    public static Game loadGame(File file) {
        Game game = (Game) loadFromStream(file);
        if (game != null) {
            game.getDatabase().openConnection();
        }
        return game;
    }

    /**
     * Serializes an Object to a specified file.
     *
     * @param file the file we want to serialize to
     * @param obj  the Object we want to serialize
     * @throws IOException if an error occurs
     */
    public static void serialize(FileOutputStream file, Object obj) throws IOException {
        mapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(file, obj);
    }

    /**
     * Serializes an Object to a Stream.
     *
     * @param file the file we want to serialize to
     * @param obj  the Object we want to serialize
     * @param type the extension of the file we serialize to
     */
    public static void saveToStream(File file, Object obj, String type) {
        try {
            String fileName = file.getName().split("\\.")[0] + type;
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(obj);
            objectOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Loads an Object from a specified file as a Stream.
     *
     * @param file the file we want to load from
     * @return an Object from the specified file
     */
    private static Object loadFromStream(File file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object data = objectIn.readObject();
            fileIn.close();
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

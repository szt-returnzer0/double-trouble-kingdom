package persistence;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import model.Game;
import model.Map;

import java.io.*;

/**
 * Implementation of FileHandler class, contains static methods for saving and loading Map and Game state to and from files.
 */
// Simplify classes with Factory
public class FileHandler {

    /**
     * Saves a Map instance to a specified file.
     *
     * @param file the File we want to save to
     * @param map  the Map instance we want to save
     */
    public static void saveMap(File file, Map map) {
        saveToFile(file, map, ".dtk");
    }

    /**
     * Saves a Game instance to a specified file.
     *
     * @param file the File we want to save to
     * @param game the Game instance we want to save
     */
    public static void saveGame(File file, Game game) {
        game.getDatabase().closeConnection();
        saveToFile(file, game, ".dtk_save");
    }

    /**
     * The logic of saving an Object state to a specified file with a specified file type.
     *
     * @param file the File we want to save to
     * @param obj  the Object we want to save
     * @param type the extension of the file we save to
     */
    public static void saveToFile(File file, Object obj, String type) {
        try {
            FileOutputStream fileOut = new FileOutputStream(file + type);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(obj);
            objectOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Loads a Map instance from a specified file.
     *
     * @param file the file we want to load from
     * @return a Map instance from the specified file
     */
    public static Map loadMap(File file) {
        return (Map) loadFromFile(file);
    }

    /**
     * Loads a Game instance from a specified file.
     *
     * @param file the file we want to load from
     * @return a Game instance from the specified file
     */
    public static Game loadGame(File file) {
        Game game = (Game) loadFromFile(file);
        if (game != null) {
            game.getDatabase().openConnection();
        }
        return game;
    }

    /**
     * The logic of loading an Object state from a specified file.
     *
     * @param file the file we want to load from
     * @return an Object instance from the specified file
     */
    private static Object loadFromFile(File file) {
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

    public static void Serialize(Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(System.out, obj);
    }

    public static Object Deserialize(String content, Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        return mapper.readValue(content, obj.getClass());
    }
}

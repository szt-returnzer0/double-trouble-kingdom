package persistence;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import model.Game;
import model.Map;

import java.io.File;
import java.io.IOException;

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
        //game.getDatabase().closeConnection();
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
            Serialize(file, obj);
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
        ObjectMapper mapper = new ObjectMapper().registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        try {
            return mapper.readValue(file, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Loads a Game instance from a specified file.
     *
     * @param file the file we want to load from
     * @return a Game instance from the specified file
     */
    public static Game loadGame(File file) {
        Game game = null;
        ObjectMapper mapper = new ObjectMapper().registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        try {
            game = mapper.readValue(file, Game.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*if (game != null) {
            game.getDatabase().openConnection();
        }*/
        return game;
    }

    /**
     * The logic of loading an Object state from a specified file.
     *
     * @param file the file we want to load from
     * @return an Object instance from the specified file
     */
    private static Object loadFromFile(File file, Object obj) {
        try {
            return Deserialize(file, obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void Serialize(File file, Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);
       /* PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(Terrain.class)
                .build();

        mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);*/

        mapper.writeValue(file, obj);
    }

    public static Object Deserialize(File file, Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        return mapper.readValue(file, Game.class);
    }
}

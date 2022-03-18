package persistence;

import model.Game;
import model.Map;

import java.io.*;

public class FileHandler {

    public static void saveMap(File file, Map map) {
        saveToFile(file, map, ".dtk");
    }

    public static void saveGame(File file, Game game) {
        game.getDatabase().closeConnection();
        saveToFile(file, game, ".dtk_save");
    }

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

    public static Map loadMap(File file) {
        return (Map) loadFromFile(file);
    }

    public static Game loadGame(File file) {
        Game game = (Game) loadFromFile(file);
        if (game != null) {
            game.getDatabase().openConnection();
        }
        return game;
    }

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
}

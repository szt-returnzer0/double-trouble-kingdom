package persistence;

import model.Game;
import model.Map;

import java.io.*;

public class FileHandler {
    public static void saveToFile(File file, Object obj) {
        try {
            FileOutputStream fileOut = new FileOutputStream(file + ".dtk");
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

    public static Game loadGameState(File file) {
        return (Game) loadFromFile(file);
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

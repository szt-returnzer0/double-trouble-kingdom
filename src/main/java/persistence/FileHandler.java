package persistence;

import model.Game;
import model.Terrain;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileHandler {
    public static void saveToFile(String fileName, Object obj) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName + ".dtk");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(obj);
            objectOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Terrain[][] loadMap(String fileName) {
        return (Terrain[][]) loadFromFile(fileName);
    }

    public static Game loadGameState(String fileName) {
        return (Game) loadFromFile(fileName);
    }

    private static Object loadFromFile(String fileName) {
        try {
            FileInputStream fileIn = new FileInputStream(fileName + ".dtk");
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

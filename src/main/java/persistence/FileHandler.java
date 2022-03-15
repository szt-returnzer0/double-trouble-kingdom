package persistence;

import model.Terrain;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileHandler {
    public static void saveMapToFile(String fileName, Terrain[][] map) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName + ".dtk");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(map);
            objectOut.close();
            System.out.println("The Object  was successfully written to a file");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Terrain[][] loadMapToFile(String fileName) {
        Terrain[][] map = null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName + ".dtk");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object data;
            data = objectIn.readObject();
            map = (Terrain[][]) data;
            fileIn.close();
            System.out.println("The Object  was successfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }
}

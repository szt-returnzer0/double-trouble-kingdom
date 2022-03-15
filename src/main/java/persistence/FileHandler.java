package persistence;

import model.Terrain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    public static void saveMapToFile(String fileName, Terrain[][] map) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".dtk"))) {
            for (Terrain[] terrains : map) {
                for (Terrain terrain : terrains) {
                    writer.write(terrain.typeToString() + " ");
                }
                writer.write(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

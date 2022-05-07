package DTK_model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public enum Types {
    CASTLE("Castle", Color.LIGHT_GRAY),
    BARRACKS("Barracks", new Color(64, 37, 19)),
    BARRICADE("Barricade", new Color(208, 146, 110)),
    SHOTGUN("Shotgun", new Color(17, 15, 15)),
    SNIPER("Sniper", new Color(118, 110, 106)),
    SOLDIER("Soldier", new Color(190, 30, 30)),
    ASSASSIN("Assassin", new Color(45, 15, 80)),
    KAMIKAZE("Kamikaze", new Color(72, 18, 25)),
    DIVER("Diver", new Color(22, 107, 107)),
    CLIMBER("Climber", new Color(175, 112, 81)),
    PLAINS("Plains", Color.GREEN),
    DESERT("Desert", Color.YELLOW),
    SWAMP("Swamp", Color.BLUE),
    MOUNTAIN("Mountain", Color.DARK_GRAY),
    NONE("None"),
    NO_SELECTION("NoSelection"),
    DELETE("Delete");

    public final String text;
    public  Color color;

    Types(String text) {
        this.text = text;

    }

    Types(String text, Color color) {
        this.text = text;
        this.color = color;
    }


    public static ArrayList<Types> getSoldierTypes() {
        return new ArrayList<>(Arrays.asList(SOLDIER, ASSASSIN, KAMIKAZE, DIVER, CLIMBER));
    }

    public static ArrayList<Types> getBuildingTypes() {
        return new ArrayList<>(Arrays.asList(CASTLE, BARRACKS, BARRICADE, SHOTGUN, SNIPER));
    }

    public static ArrayList<Types> getTerrainTypes() {
        return new ArrayList<>(Arrays.asList(PLAINS, DESERT, SWAMP, MOUNTAIN));
    }

    public static ArrayList<Types> getTowerTypes() {
        return new ArrayList<>(Arrays.asList(SHOTGUN, SNIPER, BARRICADE));
    }

    public static ArrayList<Types> getUpgradeable() {
        return new ArrayList<>(Arrays.asList(SHOTGUN, SNIPER));
    }

    public static Building buildingFactory(Types objectType, int x, int y) {
        return switch (objectType) {
            case CASTLE -> new Castle(new Point(x, y));
            case BARRACKS -> new Barracks(new Point(x, y));
            case BARRICADE -> new Barricade(new Point(x, y));
            case SHOTGUN -> new Shotgun(new Point(x, y));
            case SNIPER -> new Sniper(new Point(x, y));
            default -> throw new IllegalArgumentException("Unexpected value: " + objectType);
        };
    }

    public static Terrain terrainFactory(Types objectType, ArrayList<Entity> entities) {
        return switch (objectType) {
            case PLAINS -> new Plains(entities);
            case DESERT -> new Desert(entities);
            case SWAMP -> new Swamp(entities);
            case MOUNTAIN -> new Mountain(entities);
            default -> throw new IllegalArgumentException("Unexpected value: " + objectType);
        };
    }

    public static Soldier soldierFactory(Types objectType, int x, int y) {
        return switch (objectType) {
            case SOLDIER -> new Soldier(new Point(x, y));
            case ASSASSIN -> new Assassin(new Point(x, y));
            case KAMIKAZE -> new Kamikaze(new Point(x, y));
            case DIVER -> new Diver(new Point(x, y));
            case CLIMBER -> new Climber(new Point(x, y));
            default -> throw new IllegalArgumentException("Unexpected value: " + objectType);
        };
    }


}

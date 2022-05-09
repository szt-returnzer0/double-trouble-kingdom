package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Game types which are used in the game and rendered in the game
 */
public enum Types {
    /**
     * The Castle type
     */
    CASTLE("Castle", Color.LIGHT_GRAY),
    /**
     * The Barracks type
     */
    BARRACKS("Barracks", new Color(64, 37, 19)),
    /**
     * The Barricade type
     */
    BARRICADE("Barricade", new Color(208, 146, 110)),
    /**
     * The Shotgun type
     */
    SHOTGUN("Shotgun", new Color(17, 15, 15)),
    /**
     * The Sniper type
     */
    SNIPER("Sniper", new Color(118, 110, 106)),
    /**
     * The Soldier type
     */
    SOLDIER("Soldier", new Color(190, 30, 30)),
    /**
     * The Assassin type
     */
    ASSASSIN("Assassin", new Color(45, 15, 80)),
    /**
     * The Kamikaze type
     */
    KAMIKAZE("Kamikaze", new Color(72, 18, 25)),
    /**
     * The Diver type
     */
    DIVER("Diver", new Color(22, 107, 107)),
    /**
     * The Climber type
     */
    CLIMBER("Climber", new Color(175, 112, 81)),
    /**
     * The Plains type
     */
    PLAINS("Plains", Color.GREEN),
    /**
     * The Desert type
     */
    DESERT("Desert", Color.YELLOW),
    /**
     * The Forest type
     */
    SWAMP("Swamp", Color.BLUE),
    /**
     * The Mountain type
     */
    MOUNTAIN("Mountain", Color.DARK_GRAY),
    /**
     * The None type
     */
    NONE("None"),

    /**
     * The No Select type
     */
    NO_SELECTION("NoSelection"),

    /**
     * Delete type
     */
    DELETE("Delete");

    /**
     * String value of Enum
     */
    public final String text;
    /**
     * Color value of enum
     */
    public  Color color;

    Types(String text) {
        this.text = text;

    }

    Types(String text, Color color) {
        this.text = text;
        this.color = color;
    }


    /**
     * Returns the Soldier enum types.
     * @return soldier types
     */
    public static ArrayList<Types> getSoldierTypes() {
        return new ArrayList<>(Arrays.asList(SOLDIER, ASSASSIN, KAMIKAZE, DIVER, CLIMBER));
    }

    /**
     * Returns the Building enum types.
     * @return building types
     */
    public static ArrayList<Types> getBuildingTypes() {
        return new ArrayList<>(Arrays.asList(CASTLE, BARRACKS, BARRICADE, SHOTGUN, SNIPER));
    }

    /**
     * Returns the Tile enum types.
     * @return tile types
     */
    public static ArrayList<Types> getTerrainTypes() {
        return new ArrayList<>(Arrays.asList(PLAINS, DESERT, SWAMP, MOUNTAIN));
    }

    /**
     * Returns the Tower enum types.
     * @return tower types
     */
    public static ArrayList<Types> getTowerTypes() {
        return new ArrayList<>(Arrays.asList(SHOTGUN, SNIPER, BARRICADE));
    }

    /**
     * Returns the Upgradeable Tower enum types.
     * @return upgradeable tower types
     */
    public static ArrayList<Types> getUpgradeable() {
        return new ArrayList<>(Arrays.asList(SHOTGUN, SNIPER));
    }

    /**
     * Factory method for creating a new Building instance.
     * @param objectType the type of building to create
     * @param x the x coordinate of the building
     * @param y the y coordinate of the building
     * @return the new building
     */
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

    /**
     * Factory method for creating a new Terrain instance.
     * @param objectType the type of terrain to create
     * @param entities the entities to add to the tile
     * @return the new terrain
     */
    public static Terrain terrainFactory(Types objectType, ArrayList<Entity> entities) {
        return switch (objectType) {
            case PLAINS -> new Plains(entities);
            case DESERT -> new Desert(entities);
            case SWAMP -> new Swamp(entities);
            case MOUNTAIN -> new Mountain(entities);
            default -> throw new IllegalArgumentException("Unexpected value: " + objectType);
        };
    }

    /**
     * Factory method for creating a new Soldier instance.
     * @param objectType the type of soldier to create
     * @param x the x coordinate of the soldier
     * @param y the y coordinate of the soldier
     * @return the new soldier
     */
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

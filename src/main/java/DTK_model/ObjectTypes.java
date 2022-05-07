package DTK_model;

public enum ObjectTypes {
    CASTLE("Castle"),
    BARRACKS("Barracks"),
    BARRICADE("Barricade"),
    SHOTGUN("Shotgun"),
    SNIPER("Sniper"),
    SOLDIER("Soldier"),
    ASSASSIN("Assassin"),
    KAMIKAZE("Kamikaze"),
    DIVER("Diver"),
    CLIMBER("Climber"),
    PLAINS("Plains"),
    DESERT("Desert"),
    SWAMP("Swamp"),
    MOUNTAIN("Mountain"),
    NONE("None"),
    DELETE("Delete");

    public final String type;

    ObjectTypes(String type) {
        this.type = type;
    }

    public static ObjectTypes[] getSoldierTypes() {
        return new ObjectTypes[]{SOLDIER, ASSASSIN, KAMIKAZE, DIVER, CLIMBER};
    }

    public static ObjectTypes[] getBuildingTypes() {
        return new ObjectTypes[]{CASTLE, BARRACKS, BARRICADE};
    }

    public static ObjectTypes[] getTerrainTypes() {
        return new ObjectTypes[]{PLAINS, DESERT, SWAMP, MOUNTAIN};
    }

}

package model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameFieldModel {

    private static final Map<String, String> priceList = new HashMap<>();

    public GameFieldModel() {
        genPriceList();
    }

    public static String getPrice(String unit) {
        return priceList.get(unit);
    }

    private void genPriceList() {
        priceList.put("Sol", (new Soldier(new Point(0, 0), 0)).getValue() + "g");
        priceList.put("Kam", (new Kamikaze(new Point(0, 0), 0)).getValue() + "g");
        priceList.put("Div", (new Diver(new Point(0, 0), 0)).getValue() + "g");
        priceList.put("Cli", (new Climber(new Point(0, 0), 0)).getValue() + "g");
        priceList.put("Ass", (new Assassin(new Point(0, 0), 0)).getValue() + "g");
    }
}

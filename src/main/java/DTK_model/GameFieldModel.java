package DTK_model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameFieldModel {

    /**
     * The priceList of Units.
     */
    private static final Map<String, String> priceList = new HashMap<>();

    /**
     * Constructs a new GameFieldModel.
     */
    public GameFieldModel() {
        genPriceList();
    }

    /**
     * Returns the price of a Unit.
     *
     * @param unit the Unit we query the price of
     * @return the price of the Unit
     */
    public static String getPrice(String unit) {
        return priceList.get(unit);
    }

    /**
     * Generates the priceList.
     */
    private void genPriceList() {
        priceList.put("Sol", (new Soldier(new Point(0, 0))).getValue() + "g");
        priceList.put("Kam", (new Kamikaze(new Point(0, 0))).getValue() + "g");
        priceList.put("Div", (new Diver(new Point(0, 0))).getValue() + "g");
        priceList.put("Cli", (new Climber(new Point(0, 0))).getValue() + "g");
        priceList.put("Ass", (new Assassin(new Point(0, 0))).getValue() + "g");
    }
}

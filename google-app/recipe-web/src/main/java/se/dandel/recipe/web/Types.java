package se.dandel.recipe.web;

import java.util.HashMap;
import java.util.Map;

public class Types {
    private static Map<String, String> TYPES = new HashMap<>();
    static {
        TYPES.put("FISH", "Fisk");
        TYPES.put("ITRIM_LCD", "Itrim LCD");
        TYPES.put("MEAT", "Kött");
        TYPES.put("POULTRY", "Fågel");
        TYPES.put("SIDE_ORDER_GREEN", "Grönsaker");
        TYPES.put("SIDE_ORDER_ROOT", "Rotfrukter");
        TYPES.put("VEGGIE", "Vegetariskt");
    }

    public static String get(String key) {
        return TYPES.get(key);
    }

}

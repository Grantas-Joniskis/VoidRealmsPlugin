package me.grantisj.board.apis;

import java.util.LinkedHashMap;
import java.util.Map;

public class PlaceholderManager implements Placeholder {

    private static Map<String, String> placeholders = new LinkedHashMap<>();
    public static PlaceholderManager pm = new PlaceholderManager();

    @Override
    public void add(String key, String value) {
        placeholders.put(key, value);
    }

    public String replacePlaceholders(String text) {
        for(String placeHolder : placeholders.keySet()) {
            text = text.replace(placeHolder, getValue(placeHolder));
        }
        return text;
    }

    public String getValue(String key) {
        return placeholders.get(key);
    }
}

package me.grantisj.board.apis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Placeholders {

    private static final Map<String, Object> placeholders = new HashMap<>();
    private static final List<String> placeholderList = new ArrayList<>();

    public static void addPlaceholder(String name, Object value) {
        if(!placeholderList.contains(name)) placeholderList.add(name);
        placeholders.put(name, value);
    }

    public static Map<String, Object> getPlaceholders() {
        return new HashMap<>(placeholders);
    }
    public static List<String> getPlaceholderList() {
        return new ArrayList<>(placeholderList);
    }
}

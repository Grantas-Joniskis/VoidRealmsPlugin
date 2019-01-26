package me.grantisj.board.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chat {

    public static String toColor(String text) {
        return toColor(text, false);
    }

    public static String toColor(String text, boolean reset) {
        return ChatColor.translateAlternateColorCodes('&', (reset ? "&r" : "") + text);
    }

    public static List<String> toColor(String... lines) {
        return toColor(false, lines);
    }

    public static List<String> toColor(boolean reset, String... lines) {
        return toColor(Arrays.asList(lines), reset);
    }

    public static List<String> toColor(List<String> lines) {
        return toColor(lines, false);
    }

    public static List<String> toColor(List<String> lines, boolean reset) {

        List<String> list = new ArrayList<>();
        for (int place = 0; place < lines.size(); place++) {
            list.add(toColor(lines.get(place), reset));
        }
        return list;
    }
}

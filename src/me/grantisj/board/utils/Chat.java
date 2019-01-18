package me.grantisj.board.utils;

public class Chat {

    public static String replacePlaceholders(String text) {
        for(String placeholder : Placeholders.getPlaceholderList()) {
            text = text.replace(placeholder, "" + Placeholders.getPlaceholders().get(placeholder));
        }
        return text;
    }
}

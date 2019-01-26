package me.grantisj.board.apis.placeholder;

import me.grantisj.board.apis.board.Board;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderManager implements Placeholder {

    public static PlaceholderManager phm = new PlaceholderManager();

    private static Map<String, String> placeholders = new HashMap<>();

    @Override
    public String replacePlaceholder(String text, Board.BoardScore boardScore) {
        for(String placeholder : placeholders.keySet()) {
            switch (placeholder) {
                case "%players%":
                    if (boardScore.getDisplayText().contains(placeholder)) {
                        String players[] = getValue(placeholder).split(" ");
                        int lines = (15 - (boardScore.getValue() - 1));
                        for (int i = 0; i < players.length; i++) {
                            if (!boardScore.getBoard().lineExists(lines)) boardScore.getBoard().setLine(lines, players[i]);
                            else {
                                Board.BoardScore line = boardScore.getBoard().getLine(lines);
                               if (!line.getDisplayText().contains(placeholder)) line.clearTexts();
                               boardScore.getBoard().setLine(lines, players[i]);
                            }
                            lines++;
                            if (i == 3) break;
                            if(boardScore.getBoard().scores.size() == 15) break;
                        }
                    }
                    break;
                case "%time%":
                    if (boardScore.getDisplayText().contains(placeholder)) {
                        if (boardScore.getDisplayText().contains(placeholder)) {
                            String translatedTime = translateTime(Integer.parseInt(placeholders.get(placeholder)));
                            boardScore.setDisplayText(boardScore.getDisplayText().replace(placeholder, translatedTime));
                        }
                    }
                    break;
                default:
                    if (boardScore.getDisplayText().contains(placeholder)) {
                        String replacedPlaceholder = getValue(placeholder);
                        boardScore.setDisplayText(boardScore.getDisplayText().replace(placeholder, replacedPlaceholder));
                    }
                    break;
            }
        }
        return boardScore.getDisplayText();
    }

    public String replacePlaceholders(String text) {
        for(String placeholder : placeholders.keySet()) {
            if(text.contains(placeholder)) {
                text = text.replace(placeholder, getValue(placeholder));
            }
        }
        return text;
    }

    @Override
    public void setPlaceholder(String key, String value) {
        placeholders.put(key, value);
    }

    @Override
    public String getValue(String key) {
        return placeholders.get(key);
    }

    private String translateTime(int seconds) {
        String translatedTime = "";
        int hours = seconds / 3600;
        int remainder = seconds - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        if(seconds < 3600) translatedTime = translatedTime + (mins < 10 ? "0" + mins : mins) + ":" + (secs < 10 ? "0" + secs : secs);
        else translatedTime = translatedTime + hours + ":" + (mins < 10 ? "0" + mins : mins) + ":" + (secs < 10 ? "0" + secs : secs);
        return translatedTime;
    }

    public void loadDefaultPlaceholders() {
        setPlaceholder("%players%", "&51.Test &52.Killer &53.Troller &54.DiamondAqua");
        setPlaceholder("%time%", "" + 5600);
        setPlaceholder("%server-name%", Bukkit.getServerName());
        setPlaceholder("%current-online%", "" + Bukkit.getServer().getOnlinePlayers().size());
        setPlaceholder("%max-online%", "" + Bukkit.getServer().getMaxPlayers());
    }
}

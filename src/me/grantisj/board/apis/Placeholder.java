package me.grantisj.board.apis;

import me.grantisj.board.core.board.CraftBoard;

public interface Placeholder {

    void setPlaceholder(String key, String value);
    String getValue(String key);
    String replacePlaceholder(String text, CraftBoard.BoardScore line);

}

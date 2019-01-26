package me.grantisj.board.apis.placeholder;

import me.grantisj.board.core.board.Board;

public interface Placeholder {

    void setPlaceholder(String key, String value);
    String getValue(String key);
    String replacePlaceholder(String text, Board.BoardScore line);

}

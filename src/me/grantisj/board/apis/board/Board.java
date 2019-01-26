package me.grantisj.board.apis.board;

import me.grantisj.board.apis.placeholder.PlaceholderManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Board {

    private Objective objective;
    private Scoreboard scoreboard;
    private List<String> titles;
    private int currentTitle = 0;
    private boolean titleAnimation;
    private boolean lineAnimation;

    public List<BoardScore> scores;


    public Board(String title) {
        titles = new ArrayList<>();
        scores = new ArrayList<>();
        titles.add(title);
        titleAnimation = BoardManager.bm.getTitleAnimation();
        lineAnimation = BoardManager.bm.getLineAnimation();
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("TestBoard", "dummy");
        objective.setDisplayName(toColor(PlaceholderManager.phm.replacePlaceholders(title)));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void setLine(int line, String text) {
        if (line == scores.size() + 1) {
            createScore(15 - (line - 1), text);
            return;
        } else if (line >= scores.size() + 2) {
            System.out.println("Line " + (scores.size() + 1) + " is missing!");
            return;
        }
        try {
            BoardScore scoreLine = getScore(line);
            scoreLine.setDisplayText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addText(int line, String text) {
        BoardScore score = getLine(line);
        score.addText(text);
    }

    public void setTitle(String title) {
        objective.setDisplayName(toColor(PlaceholderManager.phm.replacePlaceholders(title)));
    }

    public void addTitle(String title) {
        if(!titles.contains(title)) titles.add(title);
    }

    private void setNextTitle() {
        if(titleAnimation) {
            if (currentTitle == titles.size() - 1) {
                currentTitle = 0;
            } else currentTitle++;
            setTitle(titles.get(currentTitle));
        }
    }

    public void display(Player player) {
        player.setScoreboard(scoreboard);
    }

    public void update() {
        clear();
        setScores();
    }

    public void updateTitle() {
        setTitle(titles.get(currentTitle));
        setNextTitle();
    }

    private void clear() {
        for (String score : scoreboard.getEntries()) {
            scoreboard.resetScores(score);
        }
    }

    public boolean lineExists(int line) {
        line = 15 - (line - 1);
        for (BoardScore boardScore : scores) {
            if (boardScore.getValue() == line) {
                return true;
            }
        }
        return false;
    }

    private void setScores() {
        for (BoardScore boardScore : scores) {
            PlaceholderManager.phm.replacePlaceholder(boardScore.getDisplayText(), boardScore);
            displayScore(boardScore);
        }
    }

    private void displayScore(BoardScore score) {
        Score line = objective.getScore(toColor(PlaceholderManager.phm.replacePlaceholder(score.getDisplayText(), score)));
        line.setScore(score.getValue());
        score.setNextScore();
    }

    private void createScore(int line, String text) {
        BoardScore score = new BoardScore(line, text);
        score.setBoard(this);
        scores.add(score);
        displayScore(score);
    }

    public BoardScore getLine(int x) {
        try {
            return getScore(x);
        } catch (IOException e) {
            return null;
        }
    }

    private BoardScore getScore(int x) throws IOException {
        x = 15 - (x - 1);
        for (BoardScore boardScore : scores) {
            if (boardScore.getValue() == x) {
                return boardScore;
            }
        }
        throw new IOException("Score not found!");
    }

    private String toColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static class BoardScore {
        private int value;
        private String displayText;
        private Board board;
        private List<String> texts;
        private int currextText;

        public BoardScore(int value, String displayText) {
            this.value = value;
            this.displayText = displayText;
            this.texts = new ArrayList<>();
            currextText = 0;
            if (!displayText.equalsIgnoreCase("")) addText(displayText);
        }

        public int getValue() {
            return value;
        }

        public String getDisplayText() {
            return displayText;
        }

        public void setDisplayText(String displayText) {
            this.displayText = displayText;
        }

        private void setValue(int value) {
            this.value = value;
        }

        private void setBoard(Board board) {
            this.board = board;
        }

        public Board getBoard() {
            return board;
        }

        public void addText(String text) {
            if (!texts.contains(text)) texts.add(text);
        }

        private String getText(int num) {
            return texts.get(num);
        }

        private void setNextScore() {
            if(board.lineAnimation) {
                if (texts.size() != 0) {
                    if (currextText == texts.size() - 1) {
                        currextText = 0;
                    } else {
                        currextText++;
                    }
                    setDisplayText(texts.get(currextText));
                }
            }
        }

        public int getTextsSize() {
            return texts.size();
        }

        public void clearTexts() {
            texts.clear();
        }
    }
}
package me.grantisj.board.apis.board;

import me.grantisj.board.Main;
import me.grantisj.board.utils.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class BoardManager {

    private Map<Integer, List<String>> lines = new HashMap<>();
    private ConfigFile configFile = Main.plugin.getConfigFile();
    private World world;
    private boolean lineAnimation;
    private boolean titleAnimation;
    private int titleDelay;
    private int lineDelay;
    public int timer;

    public static BoardManager bm = new BoardManager();

    public void loadBoards() {
        if(configFile.getValue("world") == null) configFile.setValue("world", "world");
        world = Bukkit.getWorld((String) configFile.getValue("world"));
        if(configFile.getValue("title") == null) {
            List<String> titles = new ArrayList<>();
            titles.add("test title");
            configFile.setValue("title", titles);
        }
        List<String> titles = (ArrayList<String>) configFile.getValue("title");
        lines.put(0, titles);
        if(configFile.getValue("title-animation") == null) configFile.setValue("title-animation", true);
        titleAnimation = (boolean) configFile.getValue("title-animation");
        if(configFile.getValue("title-delay") == null) configFile.setValue("title-delay", 20);
        titleDelay = (int) configFile.getValue("title-delay");
        if (configFile.getConfigurationSection("content") == null) configFile.createSection("content");
        Set<String> content = configFile.getConfigurationSection("content").getKeys(false);
        if (content.size() == 0){
            Main.plugin.getLogger().info("Content is empty! Disabling plugin...");
            Bukkit.getServer().getPluginManager().disablePlugin(Main.plugin);
        }
        boolean playersUsed = false;
        for(String line : content) {
            int lineNum = Integer.parseInt(line);
            if(configFile.getValue("content." + line + ".lines") == null) {
                List<String> lines = new ArrayList<>();
                lines.add("test message");
                configFile.setValue("content." + line + ".lines", lines);
            }
            List<String> lines = (ArrayList<String>) configFile.getValue("content." + line + ".lines");
            if(lines.contains("%players%")) {
                if(!playersUsed) {
                    lines.clear();
                    lines.add("%players%");
                    playersUsed = true;
                }else lines.remove("%players%");

            }
            this.lines.put(lineNum, lines);
        }
        if(configFile.getValue("line-animation") == null) configFile.setValue("line-animation", true);
        lineAnimation = (boolean) configFile.getValue("line-animation");
        if(configFile.getValue("line-delay") == null) configFile.setValue("line-delay", 20);
        lineDelay = (int) configFile.getValue("line-delay");
    }

    public void displayBoard(Player player) {
        Board board = new Board(lines.get(0).get(0));
        for(int i = 1; i < lines.get(0).size(); i++) board.addTitle(lines.get(0).get(i));
        if(lines.size() <= 16) {
            for(int i = 1; i < lines.size(); i++) {
                List<String> lines = this.lines.get(board.scores.size()+1);
                board.setLine(board.scores.size()+1, lines.get(0));
                board.update();
                for(String line : lines) {
                    board.addText(board.scores.size(), line);
                }
                i = board.scores.size();
                if(i == 15) break;
            }
        }
        board.display(player);
        new BukkitRunnable() {
            @Override
            public void run() { board.update(); }
        }.runTaskTimer(Main.plugin, 0, lineDelay);
        new BukkitRunnable() {
            @Override
            public void run() {
                board.updateTitle();
            }
        }.runTaskTimer(Main.plugin, 0, titleDelay);
    }

    public void clearLines() {
        lines.clear();
    }

    public World getWorld() {
        return world;
    }

    public boolean getLineAnimation() {
        return lineAnimation;
    }

    public boolean getTitleAnimation() {
        return titleAnimation;
    }
}

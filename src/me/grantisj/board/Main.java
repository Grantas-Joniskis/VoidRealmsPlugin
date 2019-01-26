package me.grantisj.board;

import me.grantisj.board.apis.board.BoardManager;
import me.grantisj.board.apis.placeholder.PlaceholderManager;
import me.grantisj.board.commands.ReloadCommand;
import me.grantisj.board.listeners.PlayerJoinListener;
import me.grantisj.board.utils.ConfigFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {

    public static Main plugin;
    private ConfigFile configFile;

    @Override
    public void onEnable() {
        plugin = this;
        PlaceholderManager.phm.loadDefaultPlaceholders();
        registerCommands();
        registerEvents();
        loadConfig();
        startTimer();
        plugin.getLogger().info(plugin.getName() + " plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        plugin.getLogger().info(plugin.getName() + " plugin has been disabled!");
        plugin = null;
    }

    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    private void loadConfig() {
        configFile = new ConfigFile(this, "config.yml");
        configFile.load();
        configFile.saveDefaultConfig();
        BoardManager.bm.loadBoards();
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public void startTimer() {
        BoardManager.bm.timer = Integer.parseInt(PlaceholderManager.phm.getValue("%time%"));
        new BukkitRunnable() {
            @Override
            public void run() {
                BoardManager.bm.timer--;
                PlaceholderManager.phm.setPlaceholder("%time%", "" + BoardManager.bm.timer);
            }
        }.runTaskTimer(this, 0, 20);
    }

    public void registerCommands() {
        this.getCommand("boardreload").setExecutor(new ReloadCommand());
    }
}

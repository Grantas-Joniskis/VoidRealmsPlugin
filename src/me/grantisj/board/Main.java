package me.grantisj.board;

import me.grantisj.board.utils.Chat;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Placeholders.addPlaceholder("test", 48);
        String testString = "testlol";
        String newString = Chat.replacePlaceholders(testString);
        System.out.println(newString);
        plugin.getLogger().info(plugin.getName() + " plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        plugin.getLogger().info(plugin.getName() + " plugin has been disabled!");
        plugin = null;
    }
}

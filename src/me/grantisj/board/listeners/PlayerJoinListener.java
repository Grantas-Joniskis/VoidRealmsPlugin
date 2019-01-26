package me.grantisj.board.listeners;

import me.grantisj.board.apis.board.BoardManager;
import me.grantisj.board.apis.placeholder.PlaceholderManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(event.getPlayer().getWorld().equals(BoardManager.bm.getWorld())) {
            PlaceholderManager.phm.setPlaceholder("%current-online%", "" + Bukkit.getServer().getOnlinePlayers().size());
            BoardManager.bm.displayBoard(event.getPlayer());
        }
    }
}

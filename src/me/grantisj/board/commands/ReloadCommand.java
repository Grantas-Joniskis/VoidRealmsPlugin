package me.grantisj.board.commands;

import me.grantisj.board.Main;
import me.grantisj.board.core.board.BoardManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        Main.plugin.getConfigFile().reload();
        BoardManager.bm.clearLines();
        BoardManager.bm.loadBoards();
        for(Player player : Bukkit.getServer().getOnlinePlayers()) BoardManager.bm.displayBoard(player);
        sender.sendMessage("CraftBoard has been reloaded!");
        return true;
    }
}

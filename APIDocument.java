import me.grantisj.board.Main;
import me.grantisj.board.core.board.Board;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class APIDocument implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Creates a new instance of Board with title "Test Board"
        CraftBoard board = new CraftBoard("&5&lTest Board");
        //We can add more title lines if we want to be our title animated.
        board.addTitle("&6&lTest Board");
        // Lets create the first line of the board.
        board.setLine(1, "simple text");
        //We use board.update() method to display line on the board
        board.update();
        //We can add extra lines to the line 1 if we want to have animations.
        board.addText(1, "another text");
        //We display the board to the player!
        board.display(event.getPlayer());
        //If we want our animations to be active we can use BukkitRunnable and update the title and lines every x amount of ticks.
        new BukkitRunnable() {
            @Override
            public void run() {
                //Updates title
                board.updateTitle();
                //Updates all lines that are in the board
                board.update();
            }
        }.runTaskTimer(Main.plugin, 0, 20);
    }
}

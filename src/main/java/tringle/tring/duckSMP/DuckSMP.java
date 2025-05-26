package tringle.tring.duckSMP;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

public final class DuckSMP extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("DuckSMP plugin is enabled");
        getCommand("charms").setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("DuckSMP plugin is disabled");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("§aWelcome to the server, " + event.getPlayer().getName() + "!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)){
            sender.sendMessage("This command can only be used by players");
            return true;
        }
        if(command.getName().equalsIgnoreCase("charms")){
            openCharms(player);
        }

        return true;
    }
    private void openCharms(Player player){
        Inventory storage = Bukkit.createInventory(null, 9, "Charms");
        player.openInventory(storage);

    }

}

package tringle.tring.duckSMP;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public final class DuckSMP extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("DuckSMP plugin is enabled");

        // register commands and events
        getCommand("charms").setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);

        // create plugin folder
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("DuckSMP plugin is disabled");
    }

    // command handling
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("charms")) {
            openCharms(player);
        }

        return true;
    }

    // Charms GUI
    private void openCharms(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, "Charms"); // 3 rows
        loadInventory(player.getUniqueId(), inv);
        player.openInventory(inv);
    }

    //save charms when gui is closed
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        InventoryView view = event.getView();

        if (!view.getTitle().equals("Charms")) return;

        Player player = (Player) event.getPlayer();
        saveInventory(player.getUniqueId(), event.getInventory());
    }

    // save inventory to player file
    private void saveInventory(UUID uuid, Inventory inv) {
        File file = new File(getDataFolder(), uuid.toString() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (int i = 0; i < inv.getSize(); i++) {
            config.set("charms.slot" + i, inv.getItem(i));
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // load inventory from player file
    private void loadInventory(UUID uuid, Inventory inv) {
        File file = new File(getDataFolder(), uuid.toString() + ".yml");
        if (!file.exists()) return;

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = config.getItemStack("charms.slot" + i);
            inv.setItem(i, item);
        }
    }
}

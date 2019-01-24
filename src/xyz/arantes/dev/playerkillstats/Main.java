package xyz.arantes.dev.playerkillstats;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.arantes.dev.playerkillstats.database.DataManager;
import xyz.arantes.dev.playerkillstats.database.GettersAndSetters;

public class Main extends JavaPlugin implements Listener {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        loadConfig();
        loadDB();
        registerEvents();
        registerCmds();
    }

    @Override
    public void onDisable() {
        DataManager.close();
    }

    private void loadDB() {
        DataManager.openMySQL();
    }

    private void registerCmds() {
    }

    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    private void loadConfig() {
        saveDefaultConfig();
    }

    @EventHandler
    public void onKill(EntityDeathEvent e){
        if (e.getEntity().getKiller() != null){
            e.getEntity().getKiller().sendMessage("§aVocê matou um §e" + e.getEntity().getType().getName());
            int kills = GettersAndSetters.getMobKills(e.getEntity().getKiller());
            if (kills >= 0){
                e.getEntity().getKiller().sendMessage("§aVocê já matou §e" + kills + "§a jogadores.");
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if (!GettersAndSetters.playerExists(e.getPlayer())) {
            GettersAndSetters.createPlayer(e.getPlayer());
        }
    }
}

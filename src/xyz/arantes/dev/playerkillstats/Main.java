package xyz.arantes.dev.playerkillstats;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.arantes.dev.playerkillstats.commands.Statset;
import xyz.arantes.dev.playerkillstats.commands.Statsadm;
import xyz.arantes.dev.playerkillstats.commands.Status;
import xyz.arantes.dev.playerkillstats.commands.Statustop;
import xyz.arantes.dev.playerkillstats.database.DataManager;
import xyz.arantes.dev.playerkillstats.database.GettersAndSetters;
import xyz.arantes.dev.playerkillstats.listeners.EntityDeath;
import xyz.arantes.dev.playerkillstats.listeners.Interact;

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
        getCommand("status").setExecutor(new Status());
        getCommand("statsadm").setExecutor(new Statsadm());
        getCommand("statset").setExecutor(new Statset());
        getCommand("statustop").setExecutor(new Statustop());
    }

    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(new EntityDeath(), this);
        this.getServer().getPluginManager().registerEvents(new Interact(), this);
    }

    private void loadConfig() {
        saveDefaultConfig();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        GettersAndSetters.createPlayer(e.getPlayer());
    }
}

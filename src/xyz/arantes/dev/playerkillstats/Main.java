package xyz.arantes.dev.playerkillstats;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
import xyz.arantes.dev.playerkillstats.utils.A;
import xyz.arantes.dev.playerkillstats.utils.Msg;

public class Main extends JavaPlugin implements Listener {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        loadConfig();
        l();
        loadDB();
        registerEvents();
        registerCmds();
    }

    @Override
    public void onDisable() {
        DataManager.close();
    }

    private static void loadDB() {
        DataManager.openMySQL();
    }

    public static void reload() {
        plugin.saveConfig();
        Bukkit.getServer().getPluginManager().disablePlugin(plugin);
        Bukkit.getServer().getPluginManager().enablePlugin(plugin);
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

    private void l() {
        try{
            A.b();
        }catch (Exception e){
            this.getLogger().info(ChatColor.RED + "============================================");
            this.getLogger().info(ChatColor.RED + "Não foi possível fazer a conexão com a");
            this.getLogger().info(ChatColor.RED + "nossa API ou a sua licença não é válida!");
            this.getLogger().info(ChatColor.RED + "Entre em contato com o desenvolvedor para mais informações.");
            this.getLogger().info(ChatColor.RED + "============================================");
            this.getPluginLoader().disablePlugin(this);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        GettersAndSetters.createPlayer(e.getPlayer());
    }
    @EventHandler
    public void onChat(ChatMessageEvent e){

        reloadConfig();
        Player p = e.getSender();
        if (getConfig().contains("tags.jogadores")){
            if(e.getTags().contains("topkj") && (p.getName().equalsIgnoreCase(getConfig().getString("tags.jogadores")))){
                e.setTagValue("topkj", Msg.getMessage("tag_top_jogadores"));
            }
        }
        if (getConfig().contains("tags.monstros")){
            if(e.getTags().contains("topkm") && (p.getName().equalsIgnoreCase(getConfig().getString("tags.monstros")))){
                e.setTagValue("topkm", Msg.getMessage("tag_top_monstros"));
            }
        }
        if (getConfig().contains("tags.animais")){
            if(e.getTags().contains("topka") && (p.getName().equalsIgnoreCase(getConfig().getString("tags.animais")))){
                e.setTagValue("topka", Msg.getMessage("tag_top_animais"));
            }
        }
        if (getConfig().contains("tags.mortes")){
            if(e.getTags().contains("topmortes") && (p.getName().equalsIgnoreCase(getConfig().getString("tags.mortes")))){
                e.setTagValue("topmortes", Msg.getMessage("tag_top_mortes"));
            }
        }
    }

}

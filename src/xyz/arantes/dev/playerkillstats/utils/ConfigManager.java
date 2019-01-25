package xyz.arantes.dev.playerkillstats.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("Duplicates")
public class ConfigManager {

    private String filename;
    private Plugin plugin;
    private FileConfiguration filecfg;
    private File file;

    public ConfigManager(Plugin plugin, String filename){
        this.plugin = plugin;
        this.filename = filename;
        setup();
    }

    private void setup(){
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
        }

        file = new File(plugin.getDataFolder(), filename);

        if(!file.exists()){
            try {
                file.createNewFile();
                plugin.saveResource(filename, true);
            }catch (IOException e){
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Falha na criação do arquivo: " + filename);
            }
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Sucesso na criação do arquivo: " + filename);
        }

        filecfg = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getData(){
        return filecfg;
    }

    public void saveFile(){
        try {
            filecfg.save(file);
        }catch (IOException e){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Falha ao salvar o arquivo: " + filename);
        }
    }

    public void reloadFile(){
        filecfg = YamlConfiguration.loadConfiguration(file);
    }
}

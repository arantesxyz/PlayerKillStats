package xyz.arantes.dev.playerkillstats.utils;

import org.bukkit.ChatColor;
import xyz.arantes.dev.playerkillstats.Main;

import java.util.ArrayList;
import java.util.List;

public class Msg {
    private static ConfigManager msg = new ConfigManager(Main.plugin, "messages.yml");

    public static String prefix = getMessage("prefix");

    public static String getMessage(String path){
        msg.reloadFile();
        return ChatColor.translateAlternateColorCodes('&', msg.getData().getString(path));
    }

    public static List<String> getMessagelist(String path){
        ArrayList<String> list = new ArrayList<>();
        msg.reloadFile();
        for (String s : msg.getData().getStringList(path)){
            list.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return list;
    }
}
package xyz.arantes.dev.playerkillstats.utils;

import xyz.arantes.dev.playerkillstats.Main;

public class Msg {
    private static ConfigManager msg = new ConfigManager(Main.plugin, "messages.yml");

    public static String prefix = msg.getData().getString("prefix");
}

package xyz.arantes.dev.playerkillstats.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.ChatColor;
import xyz.arantes.dev.playerkillstats.Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class A {

    public static void b() throws IOException, ClassNotFoundException {
            String licenca = Main.plugin.getConfig().getString("licenca");
            String url = "https://api.dev.arantes.xyz/licencas/check?lpluginid=293&licenca=" + licenca;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) con.getContent()));
            JsonObject rootobj = root.getAsJsonObject();

            Boolean valid = rootobj.get("valid").getAsBoolean();

            if (valid){
               String user = rootobj.get("user").getAsString();
                Main.plugin.getLogger().info(ChatColor.GREEN + "============================================");
                Main.plugin.getLogger().info(ChatColor.GREEN + "Sua licença é válida e está cadastrada para: " + user);
                Main.plugin.getLogger().info(ChatColor.GREEN + "Obrigado por comprar com a Arantes.");
                Main.plugin.getLogger().info(ChatColor.GREEN + "Entre em contato com o desenvolvedor para mais informações.");
                Main.plugin.getLogger().info(ChatColor.GREEN + "============================================");
            }else{
                Main.plugin.getLogger().info(ChatColor.RED + "============================================");
                Main.plugin.getLogger().info(ChatColor.RED + "Sua licença não é válida - O plugin será desativado.");
                Main.plugin.getLogger().info(ChatColor.RED + "Entre em contato com o desenvolvedor para mais informações.");
                Main.plugin.getLogger().info(ChatColor.RED + "============================================");
                Main.plugin.getPluginLoader().disablePlugin(Main.plugin);
            }
    }
}

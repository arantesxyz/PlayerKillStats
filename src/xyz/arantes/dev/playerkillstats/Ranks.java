package xyz.arantes.dev.playerkillstats;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.arantes.dev.playerkillstats.database.GettersAndSetters;
import xyz.arantes.dev.playerkillstats.utils.ConfigManager;

public class Ranks {

    private static ConfigManager ranks = new ConfigManager(Main.plugin, "ranks.yml");

    public static void rankCheck(Player player){
        ranks.reloadFile();
        for (String key : ranks.getData().getKeys(false)){
            if (key.equalsIgnoreCase(GettersAndSetters.getRank(player))){
                continue;
            }
            int ak = GettersAndSetters.getAnimalKills(player);
            int ap = GettersAndSetters.getPlayerKills(player);
            int am = GettersAndSetters.getMonsterKills(player);
            int d = GettersAndSetters.getDeaths(player);
            if (ak >= ranks.getData().getInt(key + ".min_animal_kill") &&
                ap >= ranks.getData().getInt(key + ".min_player_kill") &&
                am >= ranks.getData().getInt(key + ".min_monstro_kills") &&
                d >= ranks.getData().getInt(key + ".min_morte")){
                for (String s : ranks.getData().getStringList(key + ".comandos")){
                    Bukkit.dispatchCommand(Main.plugin.getServer().getConsoleSender(), s.replace("{jogador}", player.getName()));
                }
                for (String s : ranks.getData().getStringList(key + ".mensagens")){
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', s.replace("{jogador}", player.getName())));
                }
                for (String s : ranks.getData().getStringList(key + ".msg_jogador")){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', s.replace("{jogador}", player.getName())));
                }
                GettersAndSetters.updateRank(player, key);

            }
        }
    }
}
package xyz.arantes.dev.playerkillstats.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.arantes.dev.playerkillstats.Main;
import xyz.arantes.dev.playerkillstats.database.GettersAndSetters;
import xyz.arantes.dev.playerkillstats.utils.Msg;

import java.util.Map;

public class Statustop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (cmd.getName().equalsIgnoreCase("statustop")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cApenas jogadores in-game podem utilizar este comando.");
                return true;
            }

            Player player = ((Player) sender).getPlayer();
            if (!player.hasPermission("playerkillstats.statustop")) {
                player.sendMessage(Msg.getMessage("sem_perm"));
                return true;
            }

            if (args.length < 1){
                player.openInventory(topInv(player));
                return true;
            }else{
                if (args[0].equalsIgnoreCase("jogadores")){
                    sendTop10("players", player);
                }else if (args[0].equalsIgnoreCase("monstros")){
                    sendTop10("monsters", player);
                }else if (args[0].equalsIgnoreCase("animais")){
                    sendTop10("animals", player);
                }else if (args[0].equalsIgnoreCase("mortes")){
                    sendTop10("deaths", player);
                }
                return true;
            }
        }
        return false;
    }

    private Inventory topInv(Player player){
        // cabeça do jogador
        Inventory top = Bukkit.createInventory(null, 54, Msg.getMessage("top_gui"));
        ItemStack phead = new ItemStack(Material.PAPER, 1);

        ItemMeta pheadMeta = phead.getItemMeta();
        pheadMeta.setDisplayName(Msg.getMessage("top_gui"));
        phead.setItemMeta(pheadMeta);

        // animais
        ItemStack ahead = new ItemStack(Material.RAW_FISH, 1, (short) 3);
        ItemMeta aheadMeta = ahead.getItemMeta();

        aheadMeta.setLore(Msg.getMessagelist("top_animais_lore"));
        aheadMeta.setDisplayName("§aAnimais");
        ahead.setItemMeta(aheadMeta);

        // monstros
        ItemStack mhead = new ItemStack(Material.SKULL_ITEM, 1, (short) 4);
        ItemMeta mheadMeta = mhead.getItemMeta();

        mheadMeta.setLore(Msg.getMessagelist("top_monstros_lore"));
        mheadMeta.setDisplayName("§cMonstros");
        mhead.setItemMeta(mheadMeta);

        // jogadores
        ItemStack jhead = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta jheadMeta = jhead.getItemMeta();

        jheadMeta.setLore(Msg.getMessagelist("top_jogadores_lore"));
        jheadMeta.setDisplayName("§eJogadores");
        jhead.setItemMeta(jheadMeta);

        // mortes
        ItemStack rhead = new ItemStack(Material.BONE, 1);
        ItemMeta rheadMeta = rhead.getItemMeta();

        rheadMeta.setLore(Msg.getMessagelist("top_mortes_lore"));
        rheadMeta.setDisplayName("§eMortes");
        rhead.setItemMeta(rheadMeta);

        top.setItem(4, phead);
        top.setItem(20, ahead);
        top.setItem(22, mhead);
        top.setItem(24, jhead);
        top.setItem(40, rhead);
        return top;
    }

    public static void sendTop10(String type, Player player){
        Main.plugin.reloadConfig();
        if (type.equalsIgnoreCase("players")){
            Map<String, Integer> list = GettersAndSetters.getTopPlayerKills(10);
            int c= 0;
            player.sendMessage("\n§e  ===  TOP 10 kills em jogadores  ===  \n\n");
            for (String key : list.keySet()){
                c++;
                player.sendMessage(Msg.getMessage("msg_top_jogadores_kills").replace("{posicao}", c+"").replace("{jogador}", key).replace("{numero}",list.get(key)+""));
                if (c == 1){
                    Main.plugin.getConfig().set("tags.jogadores", key);
                    Main.plugin.saveConfig();
                }
            }
            player.sendMessage("\n§e  ============================  \n");
        }else if (type.equalsIgnoreCase("monsters")){
            Map<String, Integer> list = GettersAndSetters.getTopMonsterKills(10);
            int c= 0;
            player.sendMessage("\n§e  ===  TOP 10 kills em monstros  ===  \n\n");
            for (String key : list.keySet()){
                c++;
                player.sendMessage(Msg.getMessage("msg_top_monstros_kills").replace("{posicao}", c+"").replace("{jogador}", key).replace("{numero}",list.get(key)+""));
                if (c == 1){
                    Main.plugin.getConfig().set("tags.monstros", key);
                    Main.plugin.saveConfig();
                }
            }
            player.sendMessage("\n§e  ============================  \n");
        }else if (type.equalsIgnoreCase("animals")) {
            Map<String, Integer> list = GettersAndSetters.getTopAnimalKills(10);
            int c = 0;
            player.sendMessage("\n§e  ===  TOP 10 kills em animais  ===  \n\n");
            for (String key : list.keySet()) {
                c++;
                player.sendMessage(Msg.getMessage("msg_top_animais_kills").replace("{posicao}", c + "").replace("{jogador}", key).replace("{numero}", list.get(key) + ""));
                if (c == 1){
                    Main.plugin.getConfig().set("tags.animais", key);
                    Main.plugin.saveConfig();
                }
            }
            player.sendMessage("\n§e  ============================  \n");
        }else if (type.equalsIgnoreCase("deaths")) {
            Map<String, Integer> list = GettersAndSetters.getTopDeaths(10);
            int c = 0;
            player.sendMessage("\n§e  ===  TOP 10 mortes  ===  \n\n");
            for (String key : list.keySet()) {
                c++;
                player.sendMessage(Msg.getMessage("msg_top_mortes").replace("{posicao}", c + "").replace("{jogador}", key).replace("{numero}", list.get(key) + ""));
                if (c == 1){
                    Main.plugin.getConfig().set("tags.mortes", key);
                    Main.plugin.saveConfig();
                }
            }
            player.sendMessage("\n§e  ===================  \n");
        }
        Main.plugin.reloadConfig();
    }
}
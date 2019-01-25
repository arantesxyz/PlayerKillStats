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
import org.bukkit.inventory.meta.SkullMeta;
import xyz.arantes.dev.playerkillstats.database.GettersAndSetters;
import xyz.arantes.dev.playerkillstats.utils.Msg;

import java.util.ArrayList;

public class Status implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (cmd.getName().equalsIgnoreCase("status")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cApenas jogadores in-game podem utilizar este comando.");
                return true;
            }

            Player player = ((Player) sender).getPlayer();
            if (!player.hasPermission("playerkillstats.status")) {
                player.sendMessage(Msg.getMessage("sem_perm"));
                return true;
            }

            if (args.length < 1){
                if (GettersAndSetters.playerExists(player)){
                    player.openInventory(statusInv(player));
                }else{
                    player.sendMessage(Msg.getMessage("sem_registro"));
                }
                return true;
            }else{
                Player target = Bukkit.getPlayer(args[0]);

                if (GettersAndSetters.playerExists(target)){
                    player.openInventory(statusInv2(target));
                }else{
                    player.sendMessage(Msg.getMessage("sem_registro"));
                }
                return true;
            }
        }
        return false;
    }

    private Inventory statusInv(Player player){
        // cabeça do jogador
        Inventory status = Bukkit.createInventory(null, 54, Msg.getMessage("status_gui"));
        ItemStack phead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta pheadMeta = (SkullMeta) phead.getItemMeta();

        String rank = GettersAndSetters.getRank(player);
        ArrayList<String> pheadlore = new ArrayList<>();
        pheadlore.add("§7Você está no rank: §e" + rank);

        pheadMeta.setLore(pheadlore);
        pheadMeta.setDisplayName("§a"+player.getDisplayName());
        phead.setItemMeta(pheadMeta);

        // animais
        ItemStack ahead = new ItemStack(Material.RAW_FISH, 1, (short) 2);
        ItemMeta aheadMeta = ahead.getItemMeta();

        int akills = GettersAndSetters.getAnimalKills(player);
        ArrayList<String> aheadlore = new ArrayList<>();
        aheadlore.add("§7Quantidade de animais pacíficos");
        aheadlore.add("§7que você já matou.");
        aheadlore.add(" ");
        aheadlore.add("§7Você matou §e" + akills + "§7 animais.");

        aheadMeta.setLore(aheadlore);
        aheadMeta.setDisplayName("§aAnimais");
        ahead.setItemMeta(aheadMeta);

        // monstros
        ItemStack mhead = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
        ItemMeta mheadMeta = mhead.getItemMeta();

        int mkills = GettersAndSetters.getMonsterKills(player);
        ArrayList<String> mheadlore = new ArrayList<>();
        mheadlore.add("§7Quantidade de monstros");
        mheadlore.add("§7que você já matou.");
        mheadlore.add(" ");
        mheadlore.add("§7Você matou §e" + mkills + "§7 monstros.");

        mheadMeta.setLore(mheadlore);
        mheadMeta.setDisplayName("§cMonstros");
        mhead.setItemMeta(mheadMeta);

        // jogadores
        ItemStack jhead = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta jheadMeta = jhead.getItemMeta();

        int jkills = GettersAndSetters.getPlayerKills(player);
        ArrayList<String> jheadlore = new ArrayList<>();
        jheadlore.add("§7Quantidade de jogadores");
        jheadlore.add("§7que você já matou.");
        jheadlore.add(" ");
        jheadlore.add("§7Você matou §e" + jkills + "§7 jogadores.");

        jheadMeta.setLore(jheadlore);
        jheadMeta.setDisplayName("§eJogadores");
        jhead.setItemMeta(jheadMeta);

        // mortes
        ItemStack rhead = new ItemStack(Material.BONE, 1);
        ItemMeta rheadMeta = rhead.getItemMeta();

        int deaths = GettersAndSetters.getDeaths(player);
        ArrayList<String> rheadlore = new ArrayList<>();
        rheadlore.add("§7Quantidade de vezes");
        rheadlore.add("§7que você já morreu.");
        rheadlore.add(" ");
        rheadlore.add("§7Você morreu §e" + deaths + "§7 vezes.");

        rheadMeta.setLore(rheadlore);
        rheadMeta.setDisplayName("§eJogadores");
        rhead.setItemMeta(rheadMeta);

        status.setItem(4, phead);
        status.setItem(20, ahead);
        status.setItem(22, mhead);
        status.setItem(24, jhead);
        status.setItem(40, rhead);
        return status;
    }
    private Inventory statusInv2(Player player){
        // cabeça do jogador
        Inventory status = Bukkit.createInventory(null, 54, Msg.getMessage("status_gui"));
        ItemStack phead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta pheadMeta = (SkullMeta) phead.getItemMeta();

        String rank = GettersAndSetters.getRank(player);
        ArrayList<String> pheadlore = new ArrayList<>();
        pheadlore.add("§7O jogador está no rank: §e" + rank);

        pheadMeta.setLore(pheadlore);
        pheadMeta.setDisplayName("§a"+player.getDisplayName());
        phead.setItemMeta(pheadMeta);

        // animais
        ItemStack ahead = new ItemStack(Material.RAW_FISH, 1, (short) 3);
        ItemMeta aheadMeta = ahead.getItemMeta();

        int akills = GettersAndSetters.getAnimalKills(player);
        ArrayList<String> aheadlore = new ArrayList<>();
        aheadlore.add("§7Quantidade de animais pacíficos");
        aheadlore.add("§7que ele já matou.");
        aheadlore.add(" ");
        aheadlore.add("§7Ele matou §e" + akills + "§7 animais.");

        aheadMeta.setLore(aheadlore);
        aheadMeta.setDisplayName("§aAnimais");
        ahead.setItemMeta(aheadMeta);

        // monstros
        ItemStack mhead = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
        ItemMeta mheadMeta = mhead.getItemMeta();

        int mkills = GettersAndSetters.getMonsterKills(player);
        ArrayList<String> mheadlore = new ArrayList<>();
        mheadlore.add("§7Quantidade de monstros");
        mheadlore.add("§7que ele já matou.");
        mheadlore.add(" ");
        mheadlore.add("§7Ele matou §e" + mkills + "§7 monstros.");

        mheadMeta.setLore(mheadlore);
        mheadMeta.setDisplayName("§cMonstros");
        mhead.setItemMeta(mheadMeta);

        // jogadores
        ItemStack jhead = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta jheadMeta = jhead.getItemMeta();

        int jkills = GettersAndSetters.getPlayerKills(player);
        ArrayList<String> jheadlore = new ArrayList<>();
        jheadlore.add("§7Quantidade de jogadores");
        jheadlore.add("§7que ele já matou.");
        jheadlore.add(" ");
        jheadlore.add("§7Ele matou §e" + jkills + "§7 jogadores.");

        jheadMeta.setLore(jheadlore);
        jheadMeta.setDisplayName("§eJogadores");
        jhead.setItemMeta(jheadMeta);

        // mortes
        ItemStack rhead = new ItemStack(Material.BONE, 1);
        ItemMeta rheadMeta = rhead.getItemMeta();

        int deaths = GettersAndSetters.getDeaths(player);
        ArrayList<String> rheadlore = new ArrayList<>();
        rheadlore.add("§7Quantidade de vezes");
        rheadlore.add("§7que ele já morreu.");
        rheadlore.add(" ");
        rheadlore.add("§7Ele morreu §e" + deaths + "§7 vezes.");

        rheadMeta.setLore(rheadlore);
        rheadMeta.setDisplayName("§eJogadores");
        rhead.setItemMeta(rheadMeta);

        status.setItem(4, phead);
        status.setItem(20, ahead);
        status.setItem(22, mhead);
        status.setItem(24, jhead);
        status.setItem(40, rhead);
        return status;
    }
}

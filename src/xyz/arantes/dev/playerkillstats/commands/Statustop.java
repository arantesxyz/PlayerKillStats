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
import xyz.arantes.dev.playerkillstats.utils.Msg;

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
        ItemStack ahead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta aheadMeta = (SkullMeta) ahead.getItemMeta();

        aheadMeta.setLore(Msg.getMessagelist("top_animais_lore"));
        aheadMeta.setDisplayName("§aAnimais");
        aheadMeta.setOwner("MHF_Chicken");
        ahead.setItemMeta(aheadMeta);

        // monstros
        ItemStack mhead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta mheadMeta = (SkullMeta) mhead.getItemMeta();

        mheadMeta.setLore(Msg.getMessagelist("top_monstros_lore"));
        mheadMeta.setDisplayName("§cMonstros");
        mheadMeta.setOwner("MHF_Zombie");
        mhead.setItemMeta(mheadMeta);

        // jogadores
        ItemStack jhead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta jheadMeta = (SkullMeta) jhead.getItemMeta();

        jheadMeta.setLore(Msg.getMessagelist("top_jogadores_lore"));
        jheadMeta.setDisplayName("§eJogadores");
        jheadMeta.setOwner("MHF_Steve");
        jhead.setItemMeta(jheadMeta);

        // mortes
        ItemStack rhead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta rheadMeta = (SkullMeta) rhead.getItemMeta();

        rheadMeta.setLore(Msg.getMessagelist("top_mortes_lore"));
        rheadMeta.setDisplayName("§eJogadores");
        rheadMeta.setOwner("MHF_WSkeleton");
        rhead.setItemMeta(rheadMeta);

        top.setItem(4, phead);
        top.setItem(20, ahead);
        top.setItem(22, mhead);
        top.setItem(24, jhead);
        top.setItem(40, rhead);
        return top;
    }
}
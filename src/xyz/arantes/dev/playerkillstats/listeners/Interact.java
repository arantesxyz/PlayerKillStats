package xyz.arantes.dev.playerkillstats.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import xyz.arantes.dev.playerkillstats.commands.Statustop;
import xyz.arantes.dev.playerkillstats.utils.Msg;

public class Interact implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if (e.getClickedInventory() == null){ return; }
        if (e.getClickedInventory().getName().equalsIgnoreCase(Msg.getMessage("status_gui"))) {
            e.setCancelled(true);
        }
        if (e.getClickedInventory().getName().equalsIgnoreCase(Msg.getMessage("top_gui"))){
            if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType() == Material.AIR) || (!e.getCurrentItem().hasItemMeta())) {return;}
            e.setCancelled(true);
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aanimais")){
                Statustop.sendTop10("animals", (Player)e.getWhoClicked());
                e.getWhoClicked().closeInventory();
            }else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cMonstros")){
                Statustop.sendTop10("monsters", (Player)e.getWhoClicked());
                e.getWhoClicked().closeInventory();
            }else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eMortes")){
                Statustop.sendTop10("deaths", (Player)e.getWhoClicked());
                e.getWhoClicked().closeInventory();
            }else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eJogadores")){
                Statustop.sendTop10("players", (Player)e.getWhoClicked());
                e.getWhoClicked().closeInventory();
            }
        }

    }
}
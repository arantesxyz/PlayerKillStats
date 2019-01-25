package xyz.arantes.dev.playerkillstats.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import xyz.arantes.dev.playerkillstats.utils.Msg;

public class Interact implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if (!e.getClickedInventory().getType().equals(InventoryType.CREATIVE)){
            if (e.getClickedInventory().getName().equalsIgnoreCase(Msg.getMessage("status_gui"))){
                e.setCancelled(true);
            }
            if (e.getClickedInventory().getName().equalsIgnoreCase(Msg.getMessage("top_gui"))){
                e.setCancelled(true);
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aanimais")){
                    // TODO enviar o top
                    ((Player) e.getWhoClicked()).sendMessage("teste");
                }
            }
        }
    }
}
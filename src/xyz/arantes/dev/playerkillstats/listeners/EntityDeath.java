package xyz.arantes.dev.playerkillstats.listeners;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import xyz.arantes.dev.playerkillstats.Ranks;
import xyz.arantes.dev.playerkillstats.database.GettersAndSetters;

public class EntityDeath implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        if (e.getEntity().getKiller() != null){
            if (e.getEntity() instanceof Animals || e.getEntity() instanceof Squid){
                int kills = GettersAndSetters.getAnimalKills((e.getEntity().getKiller()));
                GettersAndSetters.updateAnimalKills(e.getEntity().getKiller(), kills +1);
            }else if (e.getEntity() instanceof Monster || e.getEntity() instanceof Slime || e.getEntity() instanceof Ghast){
                int kills = GettersAndSetters.getMonsterKills((e.getEntity().getKiller()));
                GettersAndSetters.updateMonsterKills(e.getEntity().getKiller(), kills +1);
            }
            Ranks.rankCheck(e.getEntity().getKiller());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        int deaths = GettersAndSetters.getDeaths(e.getEntity());
        GettersAndSetters.updateDeaths(e.getEntity(), deaths+1);
        Ranks.rankCheck(e.getEntity());

        if (e.getEntity().getKiller() != null){
            int kills = GettersAndSetters.getPlayerKills(e.getEntity().getKiller());
            GettersAndSetters.updatePlayerKills(e.getEntity().getKiller(), kills +1);
            Ranks.rankCheck(e.getEntity().getKiller());
        }
    }
}
package cn.mcres.karlatemp.plugins.kr;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EL implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onEIE(PlayerInteractEvent eie) {
        Player p = eie.getPlayer();
        if (p.isSneaking()) {
            return;
        }
        ItemClear.use(eie.getItem(), p.getUniqueId());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent ede) {
        C3<EntityDeathEvent, Map<UUID, INT>, Map<UUID, INT>> deathByPower = KrAPI.onEntityDeath;
        if (deathByPower != null) {
            deathByPower.run(ede, KrAPI.getKills(), KrAPI.getKarma());
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent ede) {
        C4<Player, EntityDamageEvent, Map<UUID, INT>, Map<UUID, INT>> handler = KrAPI.onDamage;
        if (handler != null) {
            Entity e = ede.getEntity();
            if (e instanceof Player) {
                handler.run((Player) e, ede, KrAPI.getKills(), KrAPI.getKarma());
            }
        }
    }
}

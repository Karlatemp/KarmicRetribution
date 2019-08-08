/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mcres.karlatemp.plugins.kr;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 *
 * @author 32798
 */
public class DefExecuter {

    public static boolean deathReset = false;
    private final Map<UUID, INT> tk = new HashMap<>();

    public void onDamage(Player p, EntityDamageEvent e, Map<UUID, INT> kills, Map<UUID, INT> karma) {
        UUID uid = p.getUniqueId();
        INT k = kills.get(uid);
        if (k == null) {
            return;
        }
        int x = k.value;
        if (isKR(uid, kills)) {
            int pow;
            if (x > 1000) {
                pow = 100 + x;
            } else if (x > 800) {
                pow = 700;
            } else if (x > 500) {
                pow = 575;
            } else if (x > 250) {
                pow = 357;
            } else if (x > 100) {
                pow = 150;
            } else {
                pow = x;
            }
            double f = e.getFinalDamage();
            if (f <= 0) {
                return;
            }
            int addx = (int) (f * pow / 150);
            if (addx > 0) {
                KrAPI.get(karma, uid).value += addx;
            }
        }
    }

    public boolean isKR(UUID uid, Map<UUID, INT> kills) {
        return KrAPI.get(kills, uid).value > 10;
    }

    public void tick(Map<UUID, INT> kills, Map<UUID, INT> kar) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            UUID id = p.getUniqueId();
            INT i = kar.get(id);
            if (i != null) {
                int k = i.value;
                k = Math.min((int) p.getHealth(), k);
                if (k > 0) {
                    int ali = p.getNoDamageTicks();
                    p.setNoDamageTicks(Math.min(ali, 2));
                    if (k > 20) {
                        p.setHealth(p.getHealth() - 1);
                        k--;
                    } else {
                        INT ticks = KrAPI.get(tk, id);
                        if (ticks.value < 1) {
                            if (p.getHealth() > 1) {
                                p.setHealth(p.getHealth() - 1);
                                k--;
                                if (k > 15) {
                                    ticks.value = 5;
                                } else if (k > 10) {
                                    ticks.value = 10;
                                } else {
                                    ticks.value = 20;
                                }
                            }
                        } else {
                            ticks.value--;
                        }
                    }
                }
                i.value = k;
            }
        }
    }

    public void onEntityDeath(EntityDeathEvent ede, Map<UUID, INT> kills, Map<UUID, INT> karma) {
        LivingEntity le = ede.getEntity();
        Player killer = le.getKiller();
        if (le instanceof Player) {
            UUID u = le.getUniqueId();
            karma.remove(u);
            if (deathReset) {
                kills.remove(u);
            }
        }
        if (le instanceof Monster) {
            return;
        }
        if (le instanceof org.bukkit.entity.Slime) {
            // Knowns BUG.
            return;
        }
        if (killer != null) {
            UUID id = killer.getUniqueId();
            KrAPI.get(kills, id).value++;
        }
    }

    public void install() {
        KrAPI.onDamage = this::onDamage;
        KrAPI.ticker = this::tick;
        KrAPI.onEntityDeath = this::onEntityDeath;
        KrAPI.krchecker = this::isKR;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mcres.karlatemp.plugins.kr;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class KrAPI {

    private static final Map<UUID, INT> killcounts = new HashMap<>(),
            karma = new HashMap<>();
    public static BiConsumer<Map<UUID, INT>, Map<UUID, INT>> ticker;
    public static C4<Player, EntityDamageEvent, Map<UUID, INT>, Map<UUID, INT>> onDamage;
    public static C3<EntityDeathEvent, Map<UUID, INT>, Map<UUID, INT>> onEntityDeath;
    public static BiPredicate<UUID, Map<UUID, INT>> krchecker;

    public static boolean isKR(UUID uid) {
        if (krchecker != null) {
            return krchecker.test(uid, killcounts);
        }
        return false;
    }

    public static Map<UUID, INT> getKills() {
        return killcounts;
    }

    public static <K> INT get(Map<K, INT> map, K key) {
        synchronized (map) {
            INT i = map.get(key);
            if (i == null) {
                i = new INT();
                map.put(key, i);
            }
            return i;
        }
    }

    public static Map<UUID, INT> getKarma() {
        return karma;
    }

    public static INT getKarma(UUID uid) {
        return get(karma, uid);
    }

    public static INT getKill(UUID uid) {
        return get(killcounts, uid);
    }

    public static void reset(UUID uid) {
        killcounts.remove(uid);
    }

    public static void addKill(UUID uid) {
        getKill(uid).value++;
    }

    public static void setKillCounts(UUID uid, int count) {
        getKill(uid).value = count;
    }

    public static int getKillCounts(UUID uid) {
        return getKill(uid).value;
    }

    public static String getVersion() {
        return KarmicRetribution.karmicRetribution.getDescription().getVersion();
    }

    private KrAPI() {
    }
}

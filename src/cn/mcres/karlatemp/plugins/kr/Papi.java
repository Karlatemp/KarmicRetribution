/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mcres.karlatemp.plugins.kr;

import java.util.UUID;
import java.util.regex.Pattern;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 *
 * @author Karlatemp
 */
public class Papi extends PlaceholderExpansion {

    public static class PAI extends Papi {

        @Override
        public String getIdentifier() {
            return "kr";
        }
    }

    @Override
    public String getIdentifier() {
        return "karmicretribution";
    }

    @Override
    public String getAuthor() {
        return "Karlatemp";
    }

    @Override
    public String getVersion() {
        return KrAPI.getVersion();
    }

    public static void registerx() {
        new Papi().register();
        new PAI().register();
    }
    public static Pattern pt = Pattern.compile("_");

    @Override
    public String onRequest(OfflinePlayer p, String params) {
        if (p == null) {
            return null;
        }
        UUID uid = p.getUniqueId();
        String low = params.toLowerCase();
        switch (low) {
            case "killcount":
            case "kc": {
                return String.valueOf(KrAPI.getKillCounts(uid));
            }
            case "karma": {
                return String.valueOf(KrAPI.getKarma(uid));
            }
        }
        String[] args = pt.split(params);
        if (args.length == 0) {
            return null;
        }
        String type = args[0];
        String $1 = null, $2 = null;
        if (args.length > 1) {
            $1 = args[1];
        }
        if (args.length > 2) {
            $2 = args[2];
        }
        switch (type.toLowerCase()) {
            case "nav":
                int length = 100;
                if ($1 != null) {
                    length = Integer.decode($1);
                }
                int flags = Tools.BUILD_ALL;
                if ($2 != null) {
                    flags = Integer.decode($2);
                }
                return Tools.createHpNav(p.getPlayer(), flags, length);
            case "showifkarma": {
                int karma = KrAPI.getKarma(uid).value;
                return ww(karma != 0 ? $1 : $2);
            }
            case "showifkr": {
                int karma = KrAPI.getKarma(uid).value;
                return ww(karma != 0 || KrAPI.isKR(uid) ? $1 : $2);
            }
        }
        return null;
    }

    private String ww(String a) {
        if (a == null) {
            return "";
        }
        return a;
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        return onRequest(p, params);
    }

}

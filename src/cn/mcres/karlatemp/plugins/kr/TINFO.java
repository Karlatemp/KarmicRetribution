/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mcres.karlatemp.plugins.kr;

import java.util.function.Consumer;
import org.bukkit.entity.Player;
import cn.mcres.gyhhy.MXLib.bukkit.MXAPI;
import cn.mcres.gyhhy.MXLib.bukkit.TitleAPI;
import java.util.Arrays;
import java.util.UUID;
import org.bukkit.ChatColor;

public class TINFO implements Consumer<Player> {

    private static final String line;

    static {
        char[] c = new char[100];
        Arrays.fill(c, '|');
        line = new String(c);
    }

    @Override
    public void accept(Player t) {
        MXAPI.getTitleAPI().sendOutChat(t, build(t), TitleAPI.TextFormatType.DEFAULT);
    }

    private double pp(double a) {
        return Math.floor(a * 100) / 100;
    }

    private String build(Player t) {
        double a = t.getHealth();
        double m = t.getMaxHealth();
        UUID uid = t.getUniqueId();
        int p = KrAPI.getKarma(uid).value;

        int d = Math.min((int) (a * 100 / m), 100);
        int e = Math.min((int) (p * 100 / m),100);
        d -= e;
        StringBuilder sb = new StringBuilder();
        {
            sb.append(t.getName()).append(" HP ");
        }
        int max = 100;
        boolean kr = false;
        if (d > 0) {
            sb.append(ChatColor.YELLOW).append(line.substring(0, d));
            max -= d;
        }
        if (e > 0) {
            sb.append(ChatColor.DARK_PURPLE).append(line.substring(0, e));
            kr = true;
            max -= e;
        }
        if (max > 0) {
            sb.append(ChatColor.RED).append(line.substring(0, max));
        }
        sb.append(" ");
        sb.append(ChatColor.RESET);
        if (kr || KrAPI.isKR(uid)) {
            sb.append("KR ");
        }
        if (kr) {
            sb.append(ChatColor.DARK_PURPLE);
        }
        sb.append(pp(a)).append('/').append(pp(m));
        return sb.toString();
    }

}

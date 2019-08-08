/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mcres.karlatemp.plugins.kr;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author 32798
 */
public class HpShow implements Runnable {
    
    private static final java.util.function.Consumer<Player> u = HpShow::update;
    static java.util.function.Consumer<Player> go;

    public static void update(Player p) {
        if (go != null) {
            go.accept(p);
        }
    }
    
    public void run() {
        Bukkit.getServer().getOnlinePlayers().forEach(u);
    }
    
}

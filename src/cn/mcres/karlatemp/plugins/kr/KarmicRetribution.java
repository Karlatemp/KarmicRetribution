package cn.mcres.karlatemp.plugins.kr;

import cn.mcres.karlatemp.plugins.kr.mm.EListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class KarmicRetribution extends JavaPlugin {

    static KarmicRetribution karmicRetribution;

    static {
        reset();
    }

    public static void reset() {
        new DefExecuter().install();
    }

    public static String get(String[] a, int index) {
        if (a != null) {
            if (index > -1 && index < a.length) {
                return a[index];
            }
        }
        return null;
    }

    public void onDisable() {
        try {
            Storex.save();
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void onEnable() {
        karmicRetribution = this;
        File dir = this.getDataFolder();
        Storex.dir = dir;
        Storex.save1 = new File(dir, "kills.bin");
        Storex.save2 = new File(dir, "karma.bin");
        Server s = getServer();
        PluginManager pm = s.getPluginManager();
        pm.registerEvents(new EL(), this);
        new MCommand(getCommand("kr"));
        s.getScheduler().runTaskTimer(this, () -> {
            int i = s.getOnlinePlayers().size();
            if (i > 0) {
                BiConsumer<Map<UUID, INT>, Map<UUID, INT>> tk = KrAPI.ticker;
                if (tk != null) {
                    tk.accept(KrAPI.getKills(), KrAPI.getKarma());
                }
            }
        }, 0, 0);
        if (pm.isPluginEnabled("MXBukkitLib") && (!getConfig().getBoolean("noact", false))) {
            HpShow.go = new TINFO();
        }
        s.getScheduler().runTaskTimer(this, new HpShow(), 5, 5);
        s.getScheduler().runTaskTimer(this, () -> {
            try {
                Storex.save();
            } catch (IOException ex) {
                getLogger().log(Level.SEVERE, null, ex);
            }
        }, 20 * 60 * 10, 20 * 60 * 10);
        try {
            Storex.load();
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, null, ex);
        }
        if (pm.isPluginEnabled("MythicMobs")) {
            new EListener(this, false).register();
        }
        if (pm.isPluginEnabled("PlaceholderAPI")) {
            Papi.registerx();
        }
    }
}

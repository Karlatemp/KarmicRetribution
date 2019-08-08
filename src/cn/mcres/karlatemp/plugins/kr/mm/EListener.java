/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mcres.karlatemp.plugins.kr.mm;

import org.bukkit.event.Listener;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

public class EListener extends RegisteredListener implements Listener, EventExecutor {

    public EListener(Plugin pl, boolean ignoreCancelled) {
        super(null, null, null, pl, false);
    }

    @Override
    public void callEvent(Event event) throws EventException {
        execute(null, event);
    }

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public Plugin getPlugin() {
        return super.getPlugin(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Listener getListener() {
        return this;
    }

    public void register() {
        getPlugin().getLogger().info("MM Listener hooked.");
        MythicMechanicLoadEvent.getHandlerList().register(this);
    }

    @Override
    public void execute(Listener listener, Event event) throws EventException {
        if (event instanceof MythicMechanicLoadEvent) {
            MythicMechanicLoadEvent ev = (MythicMechanicLoadEvent) event;
            String kn = ev.getMechanicName();
//            getPlugin().getLogger().info(kn);
            if ("Karma".equalsIgnoreCase(kn)) {
                ev.register(new KRM(kn, ev.getConfig()));
            }
        }
    }
}

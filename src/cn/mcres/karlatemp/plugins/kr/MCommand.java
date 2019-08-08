/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mcres.karlatemp.plugins.kr;

import static cn.mcres.karlatemp.plugins.kr.KarmicRetribution.get;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

/**
 *
 * @author 32798
 */
public class MCommand implements CommandExecutor {

    public MCommand(PluginCommand c) {
        c.setExecutor(this);
    }

    @SuppressWarnings("null")
    private UUID parse(String uid) {
        try {
            return UUID.fromString(uid);
        } catch (Throwable thr) {
            Optional<? extends Player> opt = Bukkit.getOnlinePlayers().stream().filter(p -> p.getName().equalsIgnoreCase(uid)).findFirst();
            Player got = opt.orElse(null);
            if (got != null) {
                return got.getUniqueId();
            }
            for (OfflinePlayer op : Bukkit.getOfflinePlayers()) {
                if (op.getName().equalsIgnoreCase(uid)) {
                    return op.getUniqueId();
                }
            }
            return null;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        UUID uid = null;
        if (sender instanceof Entity) {
            uid = ((Entity) sender).getUniqueId();
        }
        String type = get(args, 0);
        String $2 = get(args, 2);
        if (type == null) {
            return showHelp(sender);
        }
        switch (type.toLowerCase()) {
            case "show": {
                $2 = get(args, 1);
                if ($2 == null) {
                    if (sender.hasPermission("kr.command.show.me")) {
                        if (uid == null) {
                            sender.sendMessage("\u00a7cYou must be a entity.");
                        } else {
                            sender.sendMessage("\u00a7bYour kill count is: \u00a76" + KrAPI.getKillCounts(uid));
                        }
                    } else {
                        return nope(sender);
                    }
                } else {
                    if (sender.hasPermission("kr.command.show.other")) {
                        uid = parse($2);
                        if (uid == null) {
                            sender.sendMessage("\u00a7cCannot found " + $2);
                        } else {
                            sender.sendMessage("\u00a7b" + $2 + "'s kill count is: \u00a76" + KrAPI.getKillCounts(uid));
                        }
                    } else {
                        return nope(sender);
                    }
                }
                break;
            }
            case "set": {
                String $3 = $2;
                if ($2 == null) {
                    if (!sender.hasPermission("kr.command.set.me")) {
                        return nope(sender);
                    }
                    $2 = "Your";
                } else if (!sender.hasPermission("kr.command.set.other")) {
                    return nope(sender);
                } else {
                    uid = parse($2);
                    $2 += "'s";
                }
                int num;
                try {
                    num = Integer.decode(get(args, 1));
                } catch (Exception tt) {
                    sender.sendMessage("\u00a7c/kr set <number> [player]");
                    return true;
                }
                if (uid == null) {
                    if ($3 == null) {
                        sender.sendMessage("\u00a7cYou must be a entity.");
                    } else {
                        sender.sendMessage("\u00a7cCannot found " + $3);
                    }
                } else {
                    KrAPI.getKill(uid).value = num;
                    sender.sendMessage($2 + " kill count was set to " + num);
                }
                break;
            }
            case "add": {
                String $3 = $2;
                if ($2 == null) {
                    if (!sender.hasPermission("kr.command.add.me")) {
                        return nope(sender);
                    }
                    $2 = "Your";
                } else if (!sender.hasPermission("kr.command.add.other")) {
                    return nope(sender);
                } else {
                    uid = parse($2);
                    $2 += "'s";
                }
                int num;
                try {
                    num = Integer.decode(get(args, 1));
                } catch (Exception tt) {
                    sender.sendMessage("\u00a7c/kr add <number> [player]");
                    return true;
                }
                if (uid == null) {
                    if ($3 == null) {
                        sender.sendMessage("\u00a7cYou must be a entity.");
                    } else {
                        sender.sendMessage("\u00a7cCannot found " + $3);
                    }
                } else {
                    INT i = KrAPI.getKill(uid);
                    i.value += num;
                    sender.sendMessage($2 + " kill count was set to " + i.value);
                }
                break;
            }
            case "take": {
                String $3 = $2;
                if ($2 == null) {
                    if (!sender.hasPermission("kr.command.take.me")) {
                        return nope(sender);
                    }
                    $2 = "Your";
                } else if (!sender.hasPermission("kr.command.take.other")) {
                    return nope(sender);
                } else {
                    uid = parse($2);
                    $2 += "'s";
                }
                int num;
                try {
                    num = Integer.decode(get(args, 1));
                } catch (Exception tt) {
                    sender.sendMessage("\u00a7c/kr take <number> [player]");
                    return true;
                }
                if (uid == null) {
                    if ($3 == null) {
                        sender.sendMessage("\u00a7cYou must be a entity.");
                    } else {
                        sender.sendMessage("\u00a7cCannot found " + $3);
                    }
                } else {
                    INT i = KrAPI.getKill(uid);
                    i.value -= num;
                    sender.sendMessage($2 + " kill count was set to " + i.value);
                }
                break;
            }
            case "rm": {
                if (sender.hasPermission("kr.command.rm")) {
                    if (sender instanceof InventoryHolder) {
                        ((InventoryHolder) sender).getInventory().addItem(ItemClear.make(Integer.parseInt(get(args, 1))));
                    } else {
                        sender.sendMessage("Not InventoryHolder.");
                    }
                } else {
                    return nope(sender);
                }
                break;
            }
            default: {
                showHelp(sender);
                return true;
            }
        }
        return true;
    }

    private boolean showHelp(CommandSender sender) {
        sender.sendMessage("\u00a7c/kr show [player]");
        sender.sendMessage("\u00a7c/kr set <number> [player]");
        sender.sendMessage("\u00a7c/kr add <number> [player]");
        sender.sendMessage("\u00a7c/kr take <number> [player]");
        return true;
    }

    private boolean nope(CommandSender sender) {
        sender.sendMessage("\u00a7cSorry, but you don't have the permission to perform that.");
        return true;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mcres.karlatemp.plugins.kr;

import java.util.Arrays;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author Karlatemp
 */
public class Tools {

    /**
     * Karma 部分
     */
    public static final int BUILD_KARMA = 1;
    /**
     * 正常血量部分
     */
    public static final int BUILD_HP = 1 << 1;
    /**
     * 空血部分 / 已损失血量
     */
    public static final int BUILD_LOST = 1 << 2;
    /**
     * 全部
     */
    public static final int BUILD_ALL = BUILD_KARMA | BUILD_HP | BUILD_LOST;
    /**
     * 粗体效果
     */
    public static final int STYLE_BLOD = 1 << 3;
    /**
     * 斜体效果
     */
    public static final int STYLE_ITALIC = 1 << 4;
    /**
     * MAGIC效果 (§k)
     */
    public static final int STYLE_MAGIC = 1 << 5;
    /**
     * 删除线
     */
    public static final int STYLE_STRIKETHROUGH = 1 << 6;
    /**
     * 下划线
     */
    public static final int STYLE_UNDERLINE = 1 << 7;

    public static int withCharColor(int flags, ChatColor color) {
        char chara = color.getChar();
        if (chara >= '0' && chara <= '9') {
            return withChatColor(flags, chara - '0');
        }
        if (chara >= 'a' && chara <= 'z') {
            return withChatColor(flags, chara - 'a' + 10);
        }
        if (chara >= 'A' && chara <= 'Z') {
            return withChatColor(flags, chara - 'A' + 10);
        }
        return flags;
    }

    public static int withChatColor(int flags, int color) {
        return (((color + 1) & 0xFF) << 8) | flags;
    }

    public static int getColorCode(int flags) {
        return ((flags >>> 8) & 0xFF) - 1;
    }

    public static ChatColor getColor(int flags) {
        int code = getColorCode(flags);
        if (code < 0) {
            return null;
        }
        if (code < 10) {
            return ChatColor.getByChar((char) (code + '0'));
        }
        return ChatColor.getByChar((char) (code - 10 + 'a'));
    }
    private static String line;
    public static char code = '|';

    private static String getLine(int size) {
        if (line == null || line.length() < size) {
            char[] c = new char[size];
            Arrays.fill(c, code);
            return line = new String(c);
        }
        return line;
    }

    static {
        line = getLine(100);
    }

    public static String createHpNav(Player p) {
        return createHpNav(p, BUILD_ALL);
    }

    public static String createHpNav(Player p, int flags) {
        return createHpNav(p, flags, 100);
    }

    public static String createHpNav(Player p, int flags, int length) {
        return createHpNav(p, new StringBuilder(length), flags, length).toString();
    }

    private static StringBuilder a(StringBuilder sb, int f) {
        if ((f & STYLE_BLOD) != 0) {
            sb.append(ChatColor.BOLD);
        }
        if ((f & STYLE_UNDERLINE) != 0) {
            sb.append(ChatColor.UNDERLINE);
        }
        if ((f & STYLE_ITALIC) != 0) {
            sb.append(ChatColor.ITALIC);
        }
        if ((f & STYLE_MAGIC) != 0) {
            sb.append(ChatColor.MAGIC);
        }
        if ((f & STYLE_MAGIC) != 0) {
            sb.append(ChatColor.MAGIC);
        }
        if ((f & STYLE_STRIKETHROUGH) != 0) {
            sb.append(ChatColor.STRIKETHROUGH);
        }
        return sb;
    }

    public static StringBuilder createHpNav(Player p, StringBuilder builder, int flags, int length) {
        if (builder == null || p == null) {
            return builder;
        }
        ChatColor ending = getColor(flags);
        double max = p.getMaxHealth();
        double now = Math.min(max, p.getHealth());// maxhealth 可以卡bug
        String buffer = getLine(length);
        UUID uid = p.getUniqueId();
        int karma = KrAPI.getKarma(uid).value;

        int health = (int) Math.min(now * length / max, length);
        int kr = (int) Math.min(karma * length / max, length);
        health -= kr;
        if (health > 0) {
            if ((flags & BUILD_HP) != 0) {
                a(builder.append(ChatColor.YELLOW), flags).append(buffer.substring(0, health));
            }
            length -= health;
        }
        if (kr > 0) {
            if ((flags & BUILD_KARMA) != 0) {
                a(builder.append(ChatColor.DARK_PURPLE), flags).append(buffer.substring(0, kr));
            }
            length -= kr;
        }
        if (length > 0) {
            if ((flags & BUILD_LOST) != 0) {
                a(builder.append(ChatColor.RED), flags).append(buffer.substring(0, length));
            }
        }
        if (ending != null) {
            builder.append(ending);
        }
        return builder;
    }
}

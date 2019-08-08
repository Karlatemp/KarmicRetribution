/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mcres.karlatemp.plugins.kr;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class Storex {

    static File dir;
    static File save1;
    static File save2;

    public static void write(DataOutputStream ddos, Map<UUID, INT> map) throws IOException {
        for (Map.Entry<UUID, INT> e : map.entrySet()) {
            UUID uid = e.getKey();
            ddos.writeLong(uid.getMostSignificantBits());
            ddos.writeLong(uid.getLeastSignificantBits());
            ddos.writeInt(e.getValue().intValue());
        }
    }

    private static void load(File f, Map<UUID, INT> mmp) throws IOException {
        if (f.isFile()) {
            synchronized (mmp) {
                try (FileInputStream fis = new FileInputStream(f)) {
                    try (DataInputStream dis = new DataInputStream(fis)) {
                        mmp.clear();
                        while (dis.available() >= ((Long.BYTES * 2) + Integer.BYTES)) {
                            mmp.put(new UUID(dis.readLong(), dis.readLong()), new INT(dis.readInt()));
                        }
                    }
                }
            }
        }
    }

    public static void load() throws IOException {
        load(save1, KrAPI.getKills());
        load(save2, KrAPI.getKarma());
    }

    public static void save() throws IOException {
        dir.mkdirs();
        File f = save1;
        f.createNewFile();
        Map<UUID, INT> map = KrAPI.getKills();
        synchronized (map) {
            try (FileOutputStream fos = new FileOutputStream(f)) {
                try (DataOutputStream ddos = new DataOutputStream(fos)) {
                    write(ddos, map);
                }
            }
        }

        f = save2;
        f.createNewFile();
        map = KrAPI.getKarma();
        synchronized (map) {
            try (FileOutputStream fos = new FileOutputStream(f)) {
                try (DataOutputStream ddos = new DataOutputStream(fos)) {
                    write(ddos, map);
                }
            }
        }
    }
}

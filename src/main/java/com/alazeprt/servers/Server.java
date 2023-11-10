package com.alazeprt.servers;

import com.alazeprt.http.servers.*;

import java.util.*;

public enum Server {
    Vanilla(1, VanillaInfo.class),
    CraftBukkit(2, CraftBukkitInfo.class),
    Spigot(3, SpigotInfo.class),
    Paper(4, PaperInfo.class),
    PufferFish(5, PufferFishInfo.class),
    Purpur(6, PurpurInfo.class),;
//    CatServer(7),
//    Mohist(8);

    private final int id;
    private final Class<?> clazz;

    Server(int id, Class<?> clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    public static Server getServerById(int id) {
        for (Server server : Server.values()) {
            if (server.id == id) {
                return server;
            }
        }
        return null;
    }

    public static Server getServerByName(String name) {
        for (Server server : Server.values()) {
            if (server.name().equalsIgnoreCase(name)) {
                return server;
            }
        }
        return null;
    }

    public static List<String> getServers() {
        List<String> list = new ArrayList<>();
        for(Server server : Server.values()) {
            list.add(server.name());
        }
        return list;
    }

    public static Class<?> getClassByServer(Server server) {
        return server.clazz;
    }
}

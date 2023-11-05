package com.alazeprt.servers;

import java.util.*;

public enum Server {
    Vanilla(1),
    CraftBukkit(2),
    Spigot(3),
    Paper(4),
//    PufferFish(5),
    Purpur(6);
//    CatServer(7),
//    Mohist(8);

    private final int id;

    Server(int id) {
        this.id = id;
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
}

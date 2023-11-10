package com.alazeprt.http.servers.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GetBukkitHandlerTest {
    @BeforeAll
    public static void setUp() {
        System.out.println("-------- Starting GetBukkitTest --------");
    }

    @Test
    @DisplayName("getVanillaVersions")
    public void GetBukkitTest1() {
        System.out.println(GetBukkitHandler.getVersions("Spigot"));
    }

    @Test
    @DisplayName("getBukkitVersions")
    public void GetBukkitTest2() {
        System.out.println(GetBukkitHandler.getVersions("CraftBukkit"));
    }

    @Test
    @DisplayName("getBukkitVersions")
    public void GetBukkitTest3() {
        System.out.println(GetBukkitHandler.getVersions("Vanilla"));
    }

    @Test
    @DisplayName("getVanillaServerUrl")
    public void GetBukkitTest4() {
        System.out.println(GetBukkitHandler.getDownloadUrl("Vanilla", "1.14.3"));
    }

    @Test
    @DisplayName("getSpigotServerUrl")
    public void GetBukkitTest5() {
        System.out.println(GetBukkitHandler.getDownloadUrl("Spigot", "1.8.8"));
    }

    @Test
    @DisplayName("getBukkitServerUrl")
    public void GetBukkitTest6() {
        System.out.println(GetBukkitHandler.getDownloadUrl("CraftBukkit", "1.20.1"));
    }
}

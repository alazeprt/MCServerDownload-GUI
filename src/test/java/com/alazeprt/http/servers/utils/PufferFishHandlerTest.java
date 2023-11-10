package com.alazeprt.http.servers.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

public class PufferFishHandlerTest {
    private static final PufferFishHandler PufferFishHandler = new PufferFishHandler();
    @BeforeAll
    public static void setUp() {
        System.out.println("-------- Starting PufferFishTest --------");
    }

    @Test
    @DisplayName("getPufferFishServers")
    public void getServersTest() {
        Set<String> set = PufferFishHandler.getServers();
        System.out.println(Arrays.toString(set.toArray()));
    }

    @Test
    @DisplayName("getPufferFishVersions - PufferFish")
    public void getVersionsTest1() {
        Set<String> set = PufferFishHandler.getVersions("PufferFish");
        System.out.println(Arrays.toString(set.toArray()));
    }

    @Test
    @DisplayName("getPufferFishVersions - PufferFishPlus-Purpur")
    public void getVersionsTest2() {
        Set<String> set = PufferFishHandler.getVersions("PufferFishPlus-Purpur");
        System.out.println(Arrays.toString(set.toArray()));
    }

    @Test
    @DisplayName("getPufferFishDownloadUrls")
    public void getDownloadUrlTest() {
        System.out.println(PufferFishHandler.getDownloadUrl("PufferFishPlus", "1.18"));
    }
}

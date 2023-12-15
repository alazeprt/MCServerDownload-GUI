package com.alazeprt.http.servers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class CatServerInfoTest {
    private final CatServerInfo catServerInfo = new CatServerInfo();

    @BeforeAll
    public static void setUp() {
        System.out.println("-------- Starting CatServerInfoTest --------");
    }

    @Test
    @DisplayName("getCatServerVersionsList")
    public void getVersionListTest1() {
        System.out.println(Arrays.toString(catServerInfo.getVersionList().toArray()));
    }

    @Test
    @DisplayName("getCatServerUrl - 1.12.2")
    public void getServerUrlTest1() {
        System.out.println(catServerInfo.getServerUrl("1.12.2"));
    }

    @Test
    @DisplayName("getCatServerUrl - 1.18.2")
    public void getServerUrlTest2() {
        System.out.println(catServerInfo.getServerUrl("1.18.2"));
    }
}

package com.alazeprt.http.servers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class PurpurInfoTest {
    private final PurpurInfo purpurInfo = new PurpurInfo();

    @BeforeAll
    public static void setUp() {
        System.out.println("-------- Starting PurpurInfoTest --------");
    }

    @Test
    @DisplayName("getPurpurVersionsList")
    public void getVersionListTest1() {
        System.out.println(Arrays.toString(purpurInfo.getVersionList().toArray()));
    }

    @Test
    @DisplayName("getPurpurServerUrl - 1.14.3")
    public void getServerUrlTest1() {
        System.out.println(purpurInfo.getServerUrl("1.14.3"));
    }

    @Test
    @DisplayName("getPurpurServerUrl - 1.20.1")
    public void getServerUrlTest2() {
        System.out.println(purpurInfo.getServerUrl("1.20.1"));
    }
}

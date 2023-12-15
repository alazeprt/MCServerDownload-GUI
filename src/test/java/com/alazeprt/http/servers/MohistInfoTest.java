package com.alazeprt.http.servers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MohistInfoTest {
    private final MohistInfo mohistInfo = new MohistInfo();

    @BeforeAll
    public static void setUp() {
        System.out.println("-------- Starting MohistInfoTest --------");
    }

    @Test
    @DisplayName("getMohistVersionsList")
    public void getVersionListTest1() {
        System.out.println(Arrays.toString(mohistInfo.getVersionList().toArray()));
    }

    @Test
    @DisplayName("getMohistUrl - 1.7.10")
    public void getServerUrlTest1() {
        System.out.println(mohistInfo.getServerUrl("1.12.2"));
    }

    @Test
    @DisplayName("getMohistUrl - 1.19.4")
    public void getServerUrlTest2() {
        System.out.println(mohistInfo.getServerUrl("1.19.4"));
    }
}

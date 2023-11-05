package com.alazeprt.http.servers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class PaperInfoTest {
    private final PaperInfo paperInfo = new PaperInfo();

    @BeforeAll
    public static void setUp() {
        System.out.println("-------- Starting PaperInfoTest --------");
    }

    @Test
    @DisplayName("getPaperVersionsList")
    public void getVersionListTest1() {
        System.out.println(Arrays.toString(paperInfo.getVersionList().toArray()));
    }

    @Test
    @DisplayName("getPaperServerUrl - 1.16.5")
    public void getServerUrlTest1() {
        System.out.println(paperInfo.getServerUrl("1.16.5"));
    }

    @Test
    @DisplayName("getPaperServerUrl - 1.20.1")
    public void getServerUrlTest2() {
        System.out.println(paperInfo.getServerUrl("1.20.1"));
    }
}
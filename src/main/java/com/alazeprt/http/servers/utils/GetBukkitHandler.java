package com.alazeprt.http.servers.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetBukkitHandler {
    public static String getDownloadUrl(String server, String version) {
        try {
            Element versionBody = getVersionBody(server);
            String versionUrl = null;
            for(Element element : versionBody.select("div")) {
                if(element.attr("class").equals("row vdivide")) {
                    String version2 = element.selectFirst("h2").text();
                    if(version2.equalsIgnoreCase(version)) {
                        Elements elements1 = element.select("a");
                        for(Element element1 : elements1) {
                            if(element1.attr("class").equals("btn btn-download")) {
                                versionUrl = element1.attr("href");
                                break;
                            }
                        }
                    }
                }
            }
            if(versionUrl == null) {
                throw new RuntimeException("Could not find version url");
            }
            Document document1 = Jsoup.connect(versionUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                    .get();
            Elements elements1 = document1.select("div");
            Element downloadBody = null;
            for(Element element : elements1) {
                if(element.attr("id").equals("get-download")) {
                    downloadBody = element;
                    break;
                }
            }
            if(downloadBody == null) {
                throw new RuntimeException("Could not find download body");
            }
            String downloadUrl = downloadBody.selectFirst("h2").selectFirst("a").attr("href");
            if(downloadUrl == null || downloadUrl.isEmpty()) {
                throw new RuntimeException("Could not find download url");
            }
            return downloadUrl;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getVersions(String server) {
        List<String> list = new ArrayList<>();
        Element versionBody = getVersionBody(server);
        for(Element element : versionBody.select("div")) {
            if(element.attr("class").equals("row vdivide")) {
                String version = element.selectFirst("h2").text();
                list.add(version);
            }
        }
        return list;
    }

    private static Element getVersionBody(String server) {
        try {
            System.out.println("https://getbukkit.org/download/" + server.toLowerCase() + "/");
            Document document = Jsoup.connect("https://getbukkit.org/download/" + server.toLowerCase() + "/")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                    .get();
            Elements elements = document.select("div");
            Element versionBody = null;
            for(Element element : elements) {
                if(element.attr("class").equals("col-md-12 download")) {
                    versionBody = element;
                }
            }
            if(versionBody == null) {
                throw new RuntimeException("Could not find version body");
            }
            return versionBody;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

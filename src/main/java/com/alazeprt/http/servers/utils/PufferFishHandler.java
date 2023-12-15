package com.alazeprt.http.servers.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PufferFishHandler {

    public static Set<String> getServers() {
        Set<String> stringSet = new HashSet<>();
        for(Element element : getServersElement().select("tr")) {
            if(!element.attr("id").isEmpty()) {
                stringSet.add(element.attr("id").split("_")[1].replaceAll("-?\\d+\\.\\d+(\\.\\d+)*", ""));
            }
        }
        return stringSet;
    }

    public static Set<String> getVersions(String server) {
        Set<String> stringSet = new HashSet<>();
        for(Element element : getServersElement().select("tr")) {
            if(!element.attr("id").isEmpty() && element.attr("id").split("_")[1].replaceAll("-?\\d+\\.\\d+(\\.\\d+)*", "").equalsIgnoreCase(server)) {
                String regex = "\\d+(\\.\\d+)*";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(element.attr("id").split("_")[1]);
                if(matcher.find()) {
                    stringSet.add(matcher.group());
                }
            }
        }
        return stringSet;
    }

    public static String getDownloadUrl(String server, String version) {
        try {
            for(Element element : getServersElement().select("tr")) {
                if(!element.attr("id").isEmpty() && element.attr("id").split("_")[1].replaceAll("-?\\d+\\.\\d+(\\.\\d+)*", "").equalsIgnoreCase(server)) {
                    String regex = "\\d+(\\.\\d+)*";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(element.attr("id").split("_")[1]);
                    if(matcher.find() && !matcher.group().equalsIgnoreCase(version)) {
                        continue;
                    }
                    Elements elements = element.select("a");
                    String newUrl = "https://ci.pufferfish.host/" + elements.attr("class", "jenkins-table__link model-link inside").get(0).attr("href").replace("/lastBuild", "");
                    Document document = Jsoup.connect(newUrl).get();
                    return newUrl + "/" + document.getElementsByClass("fileList").select("table").get(0).selectFirst("a").attr("href");

                }
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    private static Element getServersElement() {
        try {
            Document document = Jsoup.connect("https://ci.pufferfish.host/").get();
            Element tbody = document.getElementsByClass("jenkins-table  sortable").get(0).selectFirst("tbody");
            return tbody;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

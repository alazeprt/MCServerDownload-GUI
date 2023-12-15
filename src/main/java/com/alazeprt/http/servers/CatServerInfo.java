package com.alazeprt.http.servers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CatServerInfo implements ServerInformation {

    @Override
    public String getServerUrl(String version) {
        String url = "https://jenkins.rbqcloud.cn:30011/";
        Document document;
        try {
            document = Jsoup.connect("https://jenkins.rbqcloud.cn:30011/").get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements table = document.getElementsByClass("jenkins-table  sortable").get(0).select("tr");
        for(Element element : table) {
            if(element.attr("id").split("_").length > 1 && element.attr("id").split("_")[1].contains(version)) {
                url += element.select("a").get(0).attr("href");
                break;
            }
        }
        Document document2;
        try {
            document2 = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Element table2 = document2.getElementsByClass("fileList").get(0);
        Element content = table2.select("td").get(1);
        return url + "/" + content.selectFirst("a").attr("href");
    }

    @Override
    public List<String> getVersionList() {
        List<String> list = new ArrayList<>();
        Document document;
        try {
            document = Jsoup.connect("https://catmc.org/").get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements elements = document.select("span");
        for(Element element : elements) {
            if(!element.text().contains("Minecraft: ")) {
                continue;
            }
            Pattern pattern = Pattern.compile("(?<=Minecraft: )\\d+(\\.\\d+)+(?= \\| Forge:)");
            Matcher matcher = pattern.matcher(element.text());
            if(matcher.find()) {
                list.add(matcher.group());
            }
        }
        return list;
    }
}

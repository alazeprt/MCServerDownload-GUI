import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class InternetGet {
    ArrayList<String> VersionList = new ArrayList<>();
    public InternetGet(){

    }
    public ArrayList GetVersionList() throws IOException {
        // 解析网页
        Document document = Jsoup.connect("https://getbukkit.org/download/vanilla").get();
        Element downloadlist = document.getElementById("download");
        Elements downloadpathall = downloadlist.getElementsByClass("download-pane");
        for(Element downloadpath : downloadpathall){
            this.VersionList.add(downloadpath.getElementsByTag("h2").text());
        }
        return this.VersionList;
    }
}

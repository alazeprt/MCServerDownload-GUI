import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class InternetGet {
    ArrayList<String> VersionList = new ArrayList<>();
    public InternetGet(){

    }
    public ArrayList GetVersionList() {
        // 解析网页
        Document document = null;
        try {
            document = Jsoup.connect("https://getbukkit.org/download/vanilla").get();
        } catch (UnknownHostException ex){
            JOptionPane.showMessageDialog(null, "检测到你没有连接网络!请连接后重试!","错误",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Element downloadlist = document.getElementById("download");
        Elements downloadpathall = downloadlist.getElementsByClass("download-pane");
        for(Element downloadpath : downloadpathall){
            this.VersionList.add(downloadpath.getElementsByTag("h2").text());
        }
        return this.VersionList;
    }
}

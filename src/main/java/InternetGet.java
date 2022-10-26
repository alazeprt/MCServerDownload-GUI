import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InternetGet {
    ArrayList<String> VersionList = new ArrayList<>();
    public InternetGet(){

    }
    public ArrayList GetVersionList(String server) {
        if(server.equals("paper")){
            Document document = null;
            try {
                document = Jsoup.connect("https://api.papermc.io/v2/projects/paper").ignoreContentType(true).get();
            } catch (UnknownHostException ex) {
                JOptionPane.showMessageDialog(null, "It is detected that you are not connected to the network! Please connect and try again!","错误",JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements body = document.getElementsByTag("body");
            YamlReader a = new YamlReader(body.text());
            Object b = null;
            try {
                b = a.read();
            } catch (YamlException e) {
                throw new RuntimeException(e);
            }
            Map c = (Map) b;
            Object d = c.get("versions");
            ArrayList e = new ArrayList((ArrayList) d);
            return e;
        }
        // 解析网页
        Document document = null;
        try {
            document = Jsoup.connect("https://getbukkit.org/download/" + server).get();
        } catch (UnknownHostException ex){
            JOptionPane.showMessageDialog(null, "It is detected that you are not connected to the network! Please connect and try again!","错误",JOptionPane.ERROR_MESSAGE);
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

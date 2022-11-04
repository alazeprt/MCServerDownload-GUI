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
        if(server.equals("catserver")){
            ArrayList<String> vlist = new ArrayList<>();
            Document cat = null;
            try {
                cat = Jsoup.connect("https://jenkins.rbqcloud.cn:30011").get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Element list = cat.getElementById("projectstatus");
            Elements tr = list.getElementsByTag("tr");
            for(Element chose : tr){
                if(!(chose.getElementsByTag("tr").attr("id")).isEmpty()){
                    vlist.add(chose.getElementsByTag("tr").attr("id").replace("job_CatServer-",""));
                }
            }
            return vlist;
        }
        if(server.contains("pufferfish")){
            ArrayList<String> pfp_pp = new ArrayList<>();
            ArrayList<String> pf_pp = new ArrayList<>();
            ArrayList<String> pfp = new ArrayList<>();
            ArrayList<String> pf = new ArrayList<>();
            Document document = null;
            try {
                document = Jsoup.connect("https://ci.pufferfish.host/").ignoreContentType(true).get();
            } catch (UnknownHostException e) {
                JOptionPane.showMessageDialog(null, "检测到你没有连接网络!请连接后重试!","错误",JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Element body = document.getElementById("projectstatus");
            Elements a = body.getElementsByClass("jenkins-table__link model-link inside");
            Document a2 = Jsoup.parse(a.toString());
            Elements b = a2.getElementsByTag("a");
            for(Element c : b){
                String d = c.attr("href");
                if(d.contains("PufferfishPlus") && d.contains("Purpur")){
                    pfp_pp.add(d.replace("job/PufferfishPlus-","").replace("-Purpur/",""));
                } else if(d.contains("Purpur")){
                    pf_pp.add(d.replace("job/Pufferfish-Purpur-","").replace("/",""));
                } else if(d.contains("PufferfishPlus")){
                    pfp.add(d.replace("job/PufferfishPlus-","").replace("/",""));
                } else{
                    pf.add(d.replace("job/Pufferfish-","").replace("/",""));
                }
            }
            if(server.equals("pufferfish")){
                return pf;
            } else if(server.equals("pufferfish_purpur")){
                return pf_pp;
            } else if(server.equals("pufferfishplus")){
                return pfp;
            } else if(server.equals("pufferfishplus_purpur")){
                return pfp_pp;
            }
        }
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
        } else if(server.equals("purpur")){
            Document document = null;
            try{
                document = Jsoup.connect("https://api.purpurmc.org/v2/purpur").ignoreContentType(true).get();
            } catch (UnknownHostException ex){
                JOptionPane.showMessageDialog(null, "It is detected that you are not connected to the network! Please connect and try again!","错误",JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } catch (IOException e){
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

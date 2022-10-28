
import Download.DownloadFileWithThreadPool;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadFile {
    public DownloadFile(){

    }
    public String GetVersionDownloadURL(String version, String server) {
        if(server.contains("pufferfish")){
            // 先获取url前缀
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
                if(d.contains("PufferfishPlus") && d.contains("Purpur") && d.contains(version)){
                    pfp_pp.add(d);
                } else if(d.contains("Purpur") && d.contains(version)){
                    pf_pp.add(d);
                } else if(d.contains("PufferfishPlus") && d.contains(version)){
                    pfp.add(d);
                } else if(d.contains("Pufferfish") && d.contains(version)){
                    pf.add(d);
                }
            }
            String url = null;
            if(server.equals("pufferfish")){
                url = "https://ci.pufferfish.host/" + pf.get(0) + "lastSuccessfulBuild/artifact/";
            } else if(server.equals("pufferfishplus")){
                url = "https://ci.pufferfish.host/" + pfp.get(0) + "lastSuccessfulBuild/artifact/";
            } else if(server.equals("pufferfish_purpur")){
                url = "https://ci.pufferfish.host/" + pf_pp.get(0) + "lastSuccessfulBuild/artifact/";
            } else if(server.equals("pufferfishplus_purpur")){
                url = "https://ci.pufferfish.host/" + pfp_pp.get(0) + "lastSuccessfulBuild/artifact/";
            }
            Document document2 = null;
            try {
                document2 = Jsoup.connect(url).get();
            } catch (UnknownHostException e) {
                JOptionPane.showMessageDialog(null, "检测到你没有连接网络!请连接后重试!","错误",JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements data = null;
            Element main_panel = document2.getElementById("main-panel");
            Elements url2 = main_panel.getElementsByTag("a");
            String content = url2.toString();
            String downloadurl = null;
            boolean add = false;
            if(content.contains("build/libs") && content.contains("./*zip*/archive.zip")){
                add = true;
                url = url + "build/libs/";
                Document document3 = null;
                try {
                    document3 = Jsoup.connect(url).get();
                } catch (UnknownHostException e) {
                    JOptionPane.showMessageDialog(null, "检测到你没有连接网络!请连接后重试!","错误",JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Element main_panel_2 = document3.getElementById("main-panel");
                Elements url3 = main_panel_2.getElementsByTag("a");
                data = url3;
            } else{
                data = url2;
            }
            for(Element data1 : data){
                if(data1.attr("href").toLowerCase().contains("pufferfish") && data1.attr("href").endsWith(".jar")){
                    downloadurl = url + data1.attr("href");
                }
            }
            return downloadurl;
        }
        if(server.equals("paper")){
            // 爬取https://api.papermc.io/v2/projects/paper/versions/1.16.5查看构建列表,查找最新构建
            Font defaultfont = new Font("Microsoft YaHei UI", Font.PLAIN, 16);
            UIManager.put("OptionPane.buttonFont", defaultfont);
            UIManager.put("OptionPane.messageFont", defaultfont);
            Document document = null;
            try {
                document = Jsoup.connect("https://api.papermc.io/v2/projects/paper/versions/" + version).ignoreContentType(true).get();
            } catch (UnknownHostException e) {
                JOptionPane.showMessageDialog(null, "检测到你没有连接网络!请连接后重试!","错误",JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements a = document.getElementsByTag("body");
            YamlReader b = new YamlReader(a.text());
            Map c = null;
            try {
                c = (Map) b.read();
            } catch (YamlException e) {
                throw new RuntimeException(e);
            }
            ArrayList d = new ArrayList((ArrayList) c.get("builds"));
            int maxnum = 0;
            for(Object i : d){
                String j = i.toString();
                int k = Integer.parseInt(j);
                if(k >= maxnum){
                    maxnum = k;
                }
            }
            String filename = "paper-" + version + "-" + String.valueOf(maxnum) + ".jar";
            String url = "https://api.papermc.io/v2/projects/paper/versions/" + version + "/builds/" + maxnum + "/downloads/" + filename;
            return url;
        }
        else if(server.equals("purpur")){
            String url = "https://api.purpurmc.org/v2/purpur/" + version + "/latest/download";
            return url;
        }
        Font defaultfont = new Font("Microsoft YaHei UI", Font.PLAIN, 16);
        UIManager.put("OptionPane.buttonFont", defaultfont);
        UIManager.put("OptionPane.messageFont", defaultfont);
        Elements alist = null;
        // 解析网页
        Document document = null;
        try {
            document = Jsoup.connect("https://getbukkit.org/download/" + server).get();
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null, "检测到你没有连接网络!请连接后重试!","错误",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Element downloadlist = document.getElementById("download");
        Elements downloadpathall = downloadlist.getElementsByClass("download-pane");
        for (Element downloadpath : downloadpathall) {
            if (downloadpath.getElementsByTag("h2").text().equals(version)) {
                Elements a = downloadpath.getElementsByTag("a");
                alist = a;
            }
        }
        for (Element a : alist) {
            String url = extractUrls(String.valueOf(a.getElementsByClass("btn btn-download"))).toString();
            String url2 = url.replace("[","").replace("]","");
            // 查找url2中的下载链接
            Document document2 = null;
            try {
                document2 = Jsoup.connect(url2).get();
            } catch (UnknownHostException e) {
                JOptionPane.showMessageDialog(null, "It is detected that you are not connected to the network! Please connect and try again!","错误",JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Element get_download = document2.getElementById("get-download");
            Elements download_url = get_download.getElementsByTag("h2");
            String download = extractUrls(String.valueOf(download_url)).toString();
            return download.replace("[","").replace("]","");
        }
        return "Error!";
    }
    public boolean DownloadNetFile(String urls,String path) {
        // 下载网络文件
        String pathfilename = null;
        char a = path.charAt(path.length() - 1);
        if(a == '/'){
            pathfilename = path + "server.jar";
        }
        else if(a == '\\'){
            pathfilename = path + "server.jar";
        }
        else{
            pathfilename = path + "/server.jar";
        }
        DownloadFileWithThreadPool pool = new DownloadFileWithThreadPool();
        try {
            pool.getFileWithThreadPool(urls, pathfilename, 1);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    private List<String> extractUrls(String text)
    {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex ="((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }
}


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
            System.out.println(maxnum);
            String filename = "paper-" + version + "-" + String.valueOf(maxnum) + ".jar";
            String url = "https://api.papermc.io/v2/projects/paper/versions/" + version + "/builds/" + maxnum + "/downloads/" + filename;
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
            pool.getFileWithThreadPool(urls, pathfilename, 100);
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

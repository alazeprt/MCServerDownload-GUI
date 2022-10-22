
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadFile {
    public DownloadFile(){

    }
    public String GetVersionDownloadURL(String version) {
        Font defaultfont = new Font("Microsoft YaHei UI", Font.PLAIN, 16);
        UIManager.put("OptionPane.buttonFont", defaultfont);
        UIManager.put("OptionPane.messageFont", defaultfont);
        Elements alist = null;
        // 解析网页
        Document document = null;
        try {
            document = Jsoup.connect("https://getbukkit.org/download/vanilla").get();
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
                JOptionPane.showMessageDialog(null, "检测到你没有连接网络!请连接后重试!","错误",JOptionPane.ERROR_MESSAGE);
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
        int bytesum = 0;
        int byteread = 0;


        URL url = null;
        try {
            url = new URL(urls);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
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
            FileOutputStream fs = new FileOutputStream(pathfilename);
            byte[] buffer = new byte[1204];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
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

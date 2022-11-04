import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class test {
    public static void main(String[] argv) throws IOException {
        Document cat = Jsoup.connect("https://jenkins.rbqcloud.cn:30011").get();
        Element list = cat.getElementById("projectstatus");
        Elements tr = list.getElementsByTag("tr");
        for(Element chose : tr){
            if(!(chose.getElementsByTag("tr").attr("id")).isEmpty()){
                System.out.println(chose.getElementsByTag("tr").attr("id").replace("job_CatServer-",""));
            }
        }
    }
}

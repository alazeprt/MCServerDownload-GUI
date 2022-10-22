import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LanguageReader {
    public LanguageReader(){

    }

    public boolean SetLanguage(String lang) throws IOException{
        File a = new File("./src/main/java/language.yml");
        if(a.exists()){
            a.delete();
        }
        a.createNewFile();
        FileWriter b = new FileWriter(a);
        b.write("Language: " + lang);
        b.close();
        return true;
    }

    public String GetLanguage() throws IOException{
        File a = new File("./src/main/java/language.yml");
        if(!a.exists()){
            return "null";
        }
        FileReader b = new FileReader(a);
        YamlReader language = new YamlReader(b);
        Object object = language.read();
        Map map = (Map)object;
        try {
            map.get("Language").toString();
        } catch (NullPointerException e){
            return "null";
        }
        return map.get("Language").toString();
    }

    public ArrayList GetLanguageList(){
        ArrayList<String> languagelist = new ArrayList<>();
        String path = "./src/main/java/lang";
        File langlist = new File(path);
        File[] langlistf = langlist.listFiles();
        for(File lang : langlistf){
            if(!lang.isDirectory() && lang.toString().endsWith(".yml")){
                languagelist.add(lang.toString());
            }
        }
        return languagelist;
    }

    public ArrayList GetLanguageInfo(String lang) throws IOException{
        ArrayList<String> languageinfo = new ArrayList<>();
        String path = "./src/main/java/lang/" + lang + ".yml";
        File a = new File(path);
        if(!a.exists()){
            ArrayList<String> b = new ArrayList<>();
            b.add("FileNotFoundException");
            return b;
        }
        FileReader b = new FileReader(a);
        YamlReader c = new YamlReader(b);
        Object d = c.read();
        Map map = (Map) d;
        try {
            languageinfo.add(map.get("Language").toString());
            languageinfo.add(map.get("version").toString());
        } catch (NullPointerException e){
            ArrayList<String> f = new ArrayList<>();
            f.add("NullPointerException");
            return f;
        }
        return languageinfo;
    }

    public Map GetLanguageMessage(String lang) throws IOException{
        ArrayList<String> languagemessage = new ArrayList<>();
        String path = "./src/main/java/lang/" + lang + ".yml";
        File a = new File(path);
        if(!a.exists()){
            Map b = new HashMap();
            b.put("error","FileNotFoundException");
            return b;
        }
        FileReader b = new FileReader(a);
        YamlReader c = new YamlReader(b);
        Object d = c.read();
        Map map2 = (Map) d;
        Object e = map2.get("Message");
        Map map = (Map) e;
        return map;
    }

}

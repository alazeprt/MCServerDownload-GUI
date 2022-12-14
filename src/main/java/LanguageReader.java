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
        File a = new File("./MCServerDownload/language.yml");
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
        File a = new File("./MCServerDownload/language.yml");
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
        String path = "./MCServerDownload/lang";
        File langlist = new File(path);
        File[] langlistf = langlist.listFiles();
        if(langlistf == null){
            return languagelist;
        }
        for(File lang : langlistf){
            if(!lang.isDirectory() && lang.toString().endsWith(".yml")){
                languagelist.add(lang.toString());
            }
        }
        return languagelist;
    }

    public ArrayList GetLanguageInfo(String lang) throws IOException{
        ArrayList<String> languageinfo = new ArrayList<>();
        String path = "./MCServerDownload/lang/" + lang + ".yml";
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
        String path = "./MCServerDownload/lang/" + lang + ".yml";
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

    public void WriteLanguage() throws IOException{
        String zh_cn = "Language: \"zh_cn\"\n" +
                "version: 1.5.0\n" +
                "Message:\n" +
                "  gui_title: \"?????????????????????????????? - ???alazeprt??????\"\n" +
                "  title: \"??????????????????????????????\"\n" +
                "  choose_server: \"??????????????????: \"\n" +
                "  server_list:\n" +
                "    vanilla: \"????????? - ???????????????\"\n" +
                "    craftbukkit: \"????????? - CraftBukkit?????????\"\n" +
                "    spigot: \"????????? - Spigot?????????\"\n" +
                "    paper: \"????????? - Paper?????????\"\n" +
                "    pufferfish: \"????????? - Pufferfish?????????\"\n" +
                "    pufferfish_purpur: \"????????? - Pufferfish(purpur)?????????\"\n" +
                "    pufferfishplus: \"????????? - Pufferfish+?????????\"\n" +
                "    pufferfishplus_purpur: \"????????? - Pufferfish+(purpur)?????????\"\n" +
                "    purpur: \"????????? - Purpur?????????\"\n" +
                "    catserver: \"Forge - CatServer?????????\"\n" +
                "  choose_version: \"???????????????: \"\n" +
                "  path: \"????????????: \"\n" +
                "  choose_folder: \"??????...\"\n" +
                "  setlang: \"????????????\"\n" +
                "  download: \"???????????????\"\n" +
                "  complete: \"?????????????????????!\"\n" +
                "  error: \"?????????????????????!?????????!\"";
        String en_us = "Language: \"en_us\"\n" +
                "version: 1.5.0\n" +
                "Message:\n" +
                "  gui_title: \"Minecraft Server Download - Written By alazeprt\"\n" +
                "  title: \"Minecraft Server Download\"\n" +
                "  choose_server: \"Please Select Server: \"\n" +
                "  server_list:\n" +
                "    vanilla: \"Vanilla - Vanilla\"\n" +
                "    craftbukkit: \"Plugins - CraftBukkit\"\n" +
                "    spigot: \"Plugins - Spigot\"\n" +
                "    paper: \"Plugins - Paper\"\n" +
                "    pufferfish: \"Plugins - Pufferfish\"\n" +
                "    pufferfish_purpur: \"Plugins - Pufferfish(purpur)\"\n" +
                "    pufferfishplus: \"Plugins - Pufferfish+\"\n" +
                "    pufferfishplus_purpur: \"Plugins - Pufferfish+(purpur)\"\n" +
                "    purpur: \"Plugins - Purpur\"\n" +
                "    catserver: \"Forge - CatServer\"\n" +
                "  choose_version: \"Please Select Version: \"\n" +
                "  path: \"Download Path: \"\n" +
                "  choose_folder: \"Browse...\"\n" +
                "  setlang: \"Choose Language\"\n" +
                "  download: \"Download Server\"\n" +
                "  complete: \"Server download succeeded!\"\n" +
                "  error: \"Server download failed! Please try again!\"";
        String folder = "./MCServerDownload/lang";
        File folderf = new File(folder);
        if(!folderf.exists()){
            folderf.mkdirs();
        }
        File zh_cn_file = new File(folder + "/zh_cn.yml");
        if(zh_cn_file.exists()) zh_cn_file.delete();
        File en_us_file = new File(folder + "/en_us.yml");
        if(en_us_file.exists()) en_us_file.delete();
        zh_cn_file.createNewFile();
        en_us_file.createNewFile();
        FileWriter zh_cn_writer = new FileWriter(zh_cn_file);
        FileWriter en_us_writer = new FileWriter(en_us_file);
        zh_cn_writer.write(zh_cn);
        en_us_writer.write(en_us);
        zh_cn_writer.close();
        en_us_writer.close();
    }

}

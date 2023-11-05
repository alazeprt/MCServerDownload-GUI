package com.alazeprt.http.servers;

import com.alazeprt.http.utils.HTTPRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class PurpurInfo implements ServerInformation {
    @Override
    public String getServerUrl(String version) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(HTTPRequest.get("https://api.purpurmc.org/v2/purpur/" + version), JsonObject.class);
        String latest = jsonObject.getAsJsonObject("builds").get("latest").getAsString();
        return "https://api.purpurmc.org/v2/purpur/" + version + "/" + latest + "/download";
    }

    @Override
    public List<String> getVersionList() {
        List<String> list = new ArrayList<>();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(HTTPRequest.get("https://api.purpurmc.org/v2/purpur/"), JsonObject.class);
        for(JsonElement element : jsonObject.getAsJsonArray("versions")) {
            list.add(element.getAsString());
        }
        return list;
    }
}

package com.alazeprt.http.servers;

import com.alazeprt.http.utils.HTTPRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class PaperInfo implements ServerInformation {
    @Override
    public String getServerUrl(String version) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(HTTPRequest.get("https://api.papermc.io/v2/projects/paper/versions/" + version + "/builds"), JsonObject.class);
        JsonObject buildInfo = jsonObject.getAsJsonArray("builds").get(jsonObject.getAsJsonArray("builds").size()-1).getAsJsonObject();
        String buildId = buildInfo.get("build").getAsString();
        String buildFileName = buildInfo.getAsJsonObject("downloads").getAsJsonObject("application").get("name").getAsString();
        return "https://api.papermc.io/v2/projects/paper/versions/" + version + "/builds/" + buildId + "/downloads/" + buildFileName;
    }

    @Override
    public List<String> getVersionList() {
        List<String> versions = new ArrayList<>();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(HTTPRequest.get("https://api.papermc.io/v2/projects/paper"), JsonObject.class);
        List<JsonElement> list = jsonObject.getAsJsonArray("versions").asList();
        for(JsonElement element : list) {
            versions.add(element.getAsString());
        }
        return versions;
    }
}

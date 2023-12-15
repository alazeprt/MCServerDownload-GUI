package com.alazeprt.http.servers;

import com.alazeprt.http.utils.HTTPRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class MohistInfo implements ServerInformation {
    @Override
    public String getServerUrl(String version) {
        return "https://mohistmc.com/api/v2/sources/jenkins/Mohist-" + version + "/builds/latest/download";
    }

    @Override
    public List<String> getVersionList() {
        List<String> versions = new ArrayList<>();
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(HTTPRequest.get("https://mohistmc.com/api/v2/projects"), JsonArray.class);
        for(JsonElement jsonElement : jsonArray) {
            JsonObject project = jsonElement.getAsJsonObject();
            if(!project.get("project").getAsString().equalsIgnoreCase("mohist")) {
                continue;
            }
            for(JsonElement jsonElement1 : project.getAsJsonArray("versions")) {
                versions.add(jsonElement1.getAsString());
            }
        }
        return versions;
    }
}

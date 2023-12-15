package com.alazeprt.http.servers;

import com.alazeprt.http.servers.utils.PufferFishHandler;

import java.util.ArrayList;
import java.util.List;

public class PufferFishInfo implements ServerInformation {
    @Override
    public String getServerUrl(String server_alias) {
        String version = server_alias.split("-")[server_alias.split("-").length - 1];
        StringBuilder server = new StringBuilder();
        for(String s : server_alias.split("-")) {
            if(s.equalsIgnoreCase(version)) {
                server.deleteCharAt(server.length() - 1);
                break;
            }
            server.append(s).append("-");
        }
        return PufferFishHandler.getDownloadUrl(server.toString(), version);
    }

    @Override
    public List<String> getVersionList() {
        List<String> list = new ArrayList<>();
        for(String server : PufferFishHandler.getServers()) {
            for(String version : PufferFishHandler.getVersions(server)) {
                list.add(server + "-" + version);
            }
        }
        return list;
    }
}

package com.alazeprt.http.servers;

import com.alazeprt.http.servers.utils.GetBukkitHandler;

import java.util.List;

public class CraftBukkitInfo implements ServerInformation {

    @Override
    public String getServerUrl(String version) {
        return GetBukkitHandler.getDownloadUrl("CraftBukkit", version);
    }

    @Override
    public List<String> getVersionList() {
        return GetBukkitHandler.getVersions("CraftBukkit");
    }
}

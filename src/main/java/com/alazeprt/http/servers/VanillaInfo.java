package com.alazeprt.http.servers;

import com.alazeprt.http.servers.utils.GetBukkitHandler;

import java.util.List;

public class VanillaInfo implements ServerInformation {
    @Override
    public String getServerUrl(String version) {
        return GetBukkitHandler.getDownloadUrl("vanilla", version);
    }

    @Override
    public List<String> getVersionList() {
        return GetBukkitHandler.getVersions("vanilla");
    }
}

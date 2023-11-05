package com.alazeprt.http.servers;

import com.alazeprt.http.serverutils.GetBukkit;

import java.util.List;

public class VanillaInfo implements ServerInformation {
    @Override
    public String getServerUrl(String version) {
        return GetBukkit.getDownloadUrl("vanilla", version);
    }

    @Override
    public List<String> getVersionList() {
        return GetBukkit.getVersions("vanilla");
    }
}

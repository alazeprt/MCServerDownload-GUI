package com.alazeprt.http.servers;

import java.util.List;

public interface ServerInformation {
    String getServerUrl(String version);
    List<String> getVersionList();
}

package minecraft.client.GUI.Background.download;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import java.util.HashMap;
import java.util.Map;

import minecraft.client.entities.versions.IVersion;
import minecraft.client.entities.versions.IVersionList;
import minecraft.client.entities.versions.LatestVersionInformation;
import minecraft.client.impl.common.Observable;
import minecraft.client.net.HttpUtils;

final class MCDownloadOnlineVersionList extends Observable<String> implements IVersionList{

    private static final String JSONVERSION_LIST_URL = "https://launchermeta.mojang.com/mc/game/version_manifest.json";

    private Map<String, String> versionsToUrlMap;

    MCDownloadOnlineVersionList(){
    }

    @Override
    public void startDownload() throws Exception {
        // at first, we download the complete version list
        String jsonString = HttpUtils.httpGet(JSONVERSION_LIST_URL);
        JSONObject versionInformation = (JSONObject) JSONValue.parse(jsonString);
        JSONArray versions = (JSONArray) versionInformation.get("versions");

        versionsToUrlMap = new HashMap<>();
        // and then, for each version...
        for (Object object : versions) {
            JSONObject versionObject = (JSONObject) object;
            String id = versionObject.get("id").toString();
            versionsToUrlMap.put(id, versionObject.get("url").toString());
            notifyObservers(id);
        }
    }

    @Override
    public IVersion retrieveVersionInfo(String id) throws Exception{
        if (null == versionsToUrlMap) {
            startDownload();
        }

        String fullVersionJSONString = HttpUtils.httpGet(versionsToUrlMap.get(id));
        JSONObject fullVersionObject = (JSONObject) JSONValue.parse(fullVersionJSONString);
        // ,create a MCDownloadVersion based on it
        MCDownloadVersion version = MCDownloadVersion.fromJson(fullVersionObject);
        return version;
    }

    @Override
    public LatestVersionInformation getLatestVersionInformation() throws Exception {
        String jsonString = HttpUtils.httpGet(JSONVERSION_LIST_URL);
        JSONObject versionInformation = (JSONObject) JSONValue.parse(jsonString);
        JSONObject latest = (JSONObject)versionInformation.get("latest");
        LatestVersionInformation result = new LatestVersionInformation(latest.get("release").toString(), latest.get("snapshot").toString());
        return result;
    }
}

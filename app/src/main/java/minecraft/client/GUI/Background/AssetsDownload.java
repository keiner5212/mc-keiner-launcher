package minecraft.client.GUI.Background;

import java.io.File;
import java.util.HashMap;

import org.json.JSONObject;

import minecraft.client.GUI.Logger;
import minecraft.client.entities.Json;
import minecraft.client.entities.SharedCounter;
import minecraft.client.net.HttpRequests;
import minecraft.client.persistance.FileManager;

public class AssetsDownload implements Runnable {
    private SharedCounter progress;
    private Logger logger;
    private String assetsBaseUrl = "https://resources.download.minecraft.net/";
    private String assetsIndexurl;
    private String Path;
    private Integer totalSize;
    private String assetId;

    public AssetsDownload(SharedCounter progress, Logger logger, String assetsIndexurl, String Path, Integer totalSize,
            String assetId) {
        this.assetId = assetId;
        this.totalSize = totalSize;
        this.Path = Path;
        this.progress = progress;
        this.logger = logger;
        this.assetsIndexurl = assetsIndexurl;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        logger.log("Fetching assets index...");
        Json json = Json.fromJSONObject(FileManager.loadData(Path + "\\assets\\indexes\\" + assetId + ".json"));
        if (json == null) {
            json = HttpRequests.sendGetJSONHTTPRequest(assetsIndexurl, true);
            FileManager.saveData(Path + "\\assets\\indexes\\" + assetId + ".json",
                    (json).toJSONObject());
        }
        HashMap<String, HashMap<String, Object>> assets = (HashMap<String, HashMap<String, Object>>) json.getContent()
                .get("objects");
        for (String asset : assets.keySet()) {
            HashMap<String, Object> assetInfo = assets.get(asset);
            String hash = assetInfo.get("hash").toString();
            String url = assetsBaseUrl + hash.substring(0, 2) + "/" + hash;
            progress.incrementValue(Integer.parseInt(assetInfo.get("size").toString()));
            logger.progress(progress, totalSize);
            File file = new File(Path + "\\assets\\objects\\" + hash.substring(0, 2) + "/" + hash);
            if (file.exists()) {
                logger.log("Skipping asset " + asset + "...");
                continue;
            }
            HttpRequests.downloadFile(url, Path + "\\assets\\objects\\" + hash.substring(0, 2) + "/" + hash);
            logger.log("Downloading asset " + asset + "...");
        }

        logger.log("Assets download complete.");
    }

}

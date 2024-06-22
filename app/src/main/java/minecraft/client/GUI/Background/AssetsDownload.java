package minecraft.client.GUI.Background;

import java.io.File;
import java.util.HashMap;

import minecraft.client.GUI.Logger;
import minecraft.client.entities.Json;
import minecraft.client.net.HttpRequests;

public class AssetsDownload implements Runnable {
    private Double progress;
    private Logger logger;
    private String assetsBaseUrl = "https://resources.download.minecraft.net/";
    private String assetsIndexurl;
    private String Path;
    Integer totalSize;

    public AssetsDownload(Double progress, Logger logger, String assetsIndexurl, String Path, Integer totalSize) {
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
        Json json = HttpRequests.sendGetJSONHTTPRequest(assetsIndexurl, true);
        HashMap<String, HashMap<String, Object>> assets = (HashMap<String, HashMap<String, Object>>) json.getContent()
                .get("objects");
        for (String asset : assets.keySet()) {
            HashMap<String, Object> assetInfo = assets.get(asset);
            String hash = assetInfo.get("hash").toString();
            String url = assetsBaseUrl + hash.substring(0, 2) + "/" + hash;
            progress += Integer.parseInt(assetInfo.get("size").toString());
            logger.progress(progress, totalSize);
            File file = new File(Path + "\\assets\\" + asset);
            if (file.exists()) {
                logger.log("Skipping asset " + asset + "...");
                continue;
            }
            HttpRequests.downloadFile(url, Path + "\\assets\\" + asset);
            logger.log("Downloading asset " + asset + "...");
        }

        logger.log("Assets download complete.");
    }

}

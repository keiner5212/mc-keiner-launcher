package minecraft.client.GUI.Background;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import minecraft.client.GUI.Logger;
import minecraft.client.api.GetVanillaUrls;
import minecraft.client.entities.Json;
import minecraft.client.entities.Operation;
import minecraft.client.net.HttpRequests;
import minecraft.client.persistance.FileManager;

/**
 *
 * @author keiner5212
 */
public class VersionDownloader implements Runnable {
    private String FabricUrl;
    private String ForgeUrl;
    private String Path;
    private String VanillaversionId;
    private Logger logger;

    public VersionDownloader(String loader, String VanillaversionId, String loaderVersion, String Path, Logger logger) {
        this.logger = logger;
        this.Path = Path;
        this.VanillaversionId = VanillaversionId.split(" ")[0];

        switch (loader) {
            case "Fabric":
                this.FabricUrl = "https://maven.fabricmc.net/net/fabricmc/fabric-loader/" + loaderVersion
                        + "/fabric-loader-"
                        + loaderVersion + ".jar";
                break;

            case "Forge":
                this.ForgeUrl = "https://files.minecraftforge.net/maven/net/minecraftforge/forge/" + loaderVersion
                        + "/forge-" + loaderVersion + "-installer.jar";
                break;

            default:
                break;
        }
    }

    @Override
    public void run() {
        this.logger.log("Fetching " + this.VanillaversionId + " version...");
        Operation operation = new Operation() {
            @SuppressWarnings("unchecked")
            @Override
            public void Run(Object... args) {
                if (args.length != 0) {
                    logger.log("Fetching " + VanillaversionId + " version complete.");
                    FileManager.saveData(Path + "\\versions\\" + VanillaversionId + "\\" + VanillaversionId + ".json",
                            ((Json) args[0]).toJSONObject());
                    // start the downloads
                    Double progress = 0.0;
                    Integer totalSize = 0;

                    // 1. download the vanilla client
                    HashMap<String, Object> content = (HashMap<String, Object>) ((Json) args[0]).getContent();

                    HashMap<String, Object> assetIndex = (HashMap<String, Object>) content.get("assetIndex");

                    totalSize += Integer.parseInt(assetIndex.get("size").toString());
                    totalSize += Integer.parseInt(assetIndex.get("totalSize").toString());

                    Thread thread = new Thread(
                            new AssetsDownload(progress, logger, assetIndex.get("url").toString(), Path, totalSize));
                    thread.start();

                    HashMap<String, Object> javaVersion = (HashMap<String, Object>) content.get("javaVersion");
                    logger.log("Minimum Java version required: " + javaVersion.toString());

                    HashMap<String, Object> downloads = (HashMap<String, Object>) content.get("downloads");
                    HashMap<String, Object> client = (HashMap<String, Object>) downloads.get("client");
                    ArrayList<HashMap<String, Object>> dependencies = (ArrayList<HashMap<String, Object>>) content
                            .get("libraries");

                    logger.log("Calculating total download size...");

                    totalSize = Integer.parseInt(client.get("size").toString());
                    for (HashMap<String, Object> dependency : dependencies) {
                        HashMap<String, Object> dependencyDownloads = (HashMap<String, Object>) dependency
                                .get("downloads");
                        HashMap<String, Object> dependencyartifact = (HashMap<String, Object>) dependencyDownloads
                                .get("artifact");
                        totalSize += Integer.parseInt(dependencyartifact.get("size").toString());
                    }

                    logger.log("Total download size (Bytes): " + totalSize);

                    logger.log("Downloading " + VanillaversionId + " client...");
                    logger.progress(progress, totalSize);

                    File file = new File(Path + "\\versions\\" + VanillaversionId + "\\" + VanillaversionId + ".jar");
                    if (!file.exists()) {
                        boolean downloaded = HttpRequests
                                .downloadFile(client.get("url").toString(),
                                        Path + "\\versions\\" + VanillaversionId + "\\" + VanillaversionId + ".jar");
                        if (!downloaded) {
                            logger.log("Failed to download " + VanillaversionId + " client. ");
                            return;
                        }
                    }

                    progress += Integer.parseInt(client.get("size").toString());
                    logger.progress(progress, totalSize);

                    logger.log("Downloading " + VanillaversionId + " client complete.");

                    // 2. download dependencies
                    logger.log("Downloading braries...");

                    for (HashMap<String, Object> dependency : dependencies) {
                        HashMap<String, Object> dependencyDownloads = (HashMap<String, Object>) dependency
                                .get("downloads");
                        HashMap<String, Object> dependencyartifact = (HashMap<String, Object>) dependencyDownloads
                                .get("artifact");
                        ArrayList<HashMap<String, Object>> dependencyrules = (ArrayList<HashMap<String, Object>>) dependency
                                .get("rules");
                        if (dependencyrules != null) {
                            for (HashMap<String, Object> rule : dependencyrules) {
                                HashMap<String, Object> os = (HashMap<String, Object>) rule.get("os");
                                if (os != null && System.getProperty("os.name").toLowerCase().contains(
                                        os.get("name").toString().toLowerCase())) {

                                    logger.log("Downloading library " + dependency.get("name").toString() + "...");

                                    file = new File(Path + "\\libraries\\" + dependencyartifact.get("path"));
                                    if (!file.exists()) {
                                        boolean downloaded = HttpRequests
                                                .downloadFile(dependencyartifact.get("url").toString(),
                                                        Path + "\\libraries\\" + dependencyartifact.get("path"));
                                        if (!downloaded) {
                                            return;
                                        }
                                    }else{
                                        logger.log("Skipping library " + dependency.get("name").toString() + "...");
                                    }

                                    progress += Integer.parseInt(dependencyartifact.get("size").toString());
                                    logger.progress(progress, totalSize);
                                }
                            }
                        } else {
                            logger.log("Downloading library " + dependency.get("name").toString() + "...");
                            file = new File(Path + "\\libraries\\" + dependencyartifact.get("path"));
                            if (!file.exists()) {
                                boolean downloaded = HttpRequests
                                        .downloadFile(dependencyartifact.get("url").toString(),
                                                Path + "\\libraries\\" + dependencyartifact.get("path"));
                                if (!downloaded) {
                                    return;
                                }
                            }else{
                                logger.log("Skipping library " + dependency.get("name").toString() + "...");
                            }
                            progress += Integer.parseInt(dependencyartifact.get("size").toString());
                            logger.progress(progress, totalSize);

                        }
                    }
                    logger.progress(totalSize, totalSize);

                    logger.log("Downloading libraries complete.");
                    try {
                        thread.join();
                    } catch (Exception e) {
                        logger.log("Failed to wait for assets download.");
                    }
                    logger.log("Download complete. " + VanillaversionId + " version is ready. ");
                    
                    // 3. download assets

                    // 4. handle the loaders (Fabric, Forge, Vanilla)

                    // 5. run the client
                } else {
                    logger.log("Failed to fetch " + VanillaversionId + " version. ");
                }
            }
        };
        Thread thread = new Thread(new GetVanillaUrls(operation, VanillaversionId.split(" ")[0], Path));
        thread.start();
    }

}

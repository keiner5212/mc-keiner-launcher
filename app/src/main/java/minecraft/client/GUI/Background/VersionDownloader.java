package minecraft.client.GUI.Background;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import minecraft.client.GUI.Logger;
import minecraft.client.api.GetVanillaUrls;
import minecraft.client.entities.Json;
import minecraft.client.entities.Operation;
import minecraft.client.net.HttpRequests;

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
        this.VanillaversionId = VanillaversionId;

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
                    // start the downloads
                    double progress = 0;
                    int totalSize = 0;

                    // 1. download the vanilla client
                    HashMap<String, Object> content = (HashMap<String, Object>) ((Json) args[0]).getContent();

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
                    logger.log("progress (Bytes): " + progress + " / " + totalSize);
                    boolean downloaded = HttpRequests
                            .downloadJarFile(client.get("url").toString(), Path + "\\client.jar");

                    progress += Integer.parseInt(client.get("size").toString());
                    logger.log("progress (Bytes): " + progress + " / " + totalSize);

                    if (!downloaded) {
                        logger.log("Failed to download " + VanillaversionId + " client. ");
                        return;
                    }

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
                                if (os != null) {
                                    if (System.getProperty("os.name").toLowerCase().contains(
                                            os.get("name").toString().toLowerCase())) {
                                        logger.log("Downloading " + dependency.get("name").toString() + " library...");
                                        downloaded = HttpRequests
                                                .downloadJarFile(dependencyartifact.get("url").toString(),
                                                        Path + "\\" + dependencyartifact.get("path"));
                                        if (!downloaded) {
                                            return;
                                        } else {
                                            progress += Integer.parseInt(dependencyartifact.get("size").toString());
                                            logger.log("progress (Bytes): " + progress + " / " + totalSize);
                                        }
                                    }
                                }

                            }
                        } else {
                            logger.log("Downloading " + dependency.get("name").toString() + " library...");
                            downloaded = HttpRequests
                                    .downloadJarFile(dependencyartifact.get("url").toString(),
                                            Path + "\\" + dependencyartifact.get("path"));
                            if (!downloaded) {
                                return;
                            } else {
                                progress += Integer.parseInt(dependencyartifact.get("size").toString());
                                logger.log("progress (Bytes): " + progress + " / " + totalSize);
                            }
                        }
                    }
                    
                    logger.log("Downloading libraries complete.");

                    // 3. handle the loaders (Fabric, Forge, Vanilla)

                    // 4.  run the client
                } else {
                    logger.log("Failed to fetch " + VanillaversionId + " version. ");
                }
            }
        };
        Thread thread = new Thread(new GetVanillaUrls(operation, VanillaversionId.split(" ")[0]));
        thread.start();
    }

}

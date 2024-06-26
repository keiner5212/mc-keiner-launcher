package minecraft.client.GUI.Background;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import minecraft.client.GUI.Logger;
import minecraft.client.GUI.Background.download.MCDownloadVersionList;
import minecraft.client.api.GetVanillaUrls;
import minecraft.client.entities.Operation;
import minecraft.client.entities.versions.IVersion;
import net.minidev.json.JSONObject;
import minecraft.client.api.common.mc.MinecraftInstance;
import minecraft.client.entities.ISession;

/**
 *
 * @author keiner5212
 */
public class VersionDownloader implements Runnable {
    private String ForgeUrl;
    private MinecraftInstance minecraftInstance;
    private String VanillaversionId;
    private Logger logger;
    private int Memory;
    private int width;
    private int height;
    private String jvm;
    private ISession session;

    public VersionDownloader(String loader, String VanillaversionId, String loaderVersion, MinecraftInstance mc,
            Logger logger,
            int Memory, ISession session, int width, int height, String jvm) {
        this.jvm = jvm;
        this.width = width;
        this.height = height;
        this.session = session;
        this.Memory = Memory;
        this.logger = logger;
        this.minecraftInstance = mc;
        this.VanillaversionId = VanillaversionId.split(" ")[0];

        switch (loader) {
            case "Fabric":
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
                    // JSONObject content = (JSONObject) args[0];
                    // FileManager.saveData(
                    //         minecraftInstance.getLocation().getAbsolutePath() + "\\versions\\" + VanillaversionId + "\\"
                    //                 + VanillaversionId + ".json",
                    //         content);

                    // 1. download the vanilla client

                    // logger.log("Downloading " + VanillaversionId + " client...");

                    // File file = new File(minecraftInstance.getLocation().getAbsolutePath() + "\\versions\\"
                    //         + VanillaversionId + "\\" + VanillaversionId + ".jar");
                    // if (!file.exists()) {
                    //     boolean downloaded = HttpRequests
                    //             .downloadFile(client.get("url").toString(),
                    //                     minecraftInstance.getLocation().getAbsolutePath() + "\\versions\\"
                    //                             + VanillaversionId + "\\" + VanillaversionId + ".jar");
                    //     if (!downloaded) {
                    //         logger.log("Failed to download " + VanillaversionId + " client. ");
                    //         return;
                    //     }
                    // }

                    // logger.log("Downloading " + VanillaversionId + " client complete.");

                    // 1. download Minecraft

                    MCDownloadVersionList mcDownloadVersionList = new MCDownloadVersionList(minecraftInstance);
                    try {
                        mcDownloadVersionList.startDownload();
                        IVersion mcVersion = mcDownloadVersionList.retrieveVersionInfo(VanillaversionId);
                        boolean isCompatible = mcVersion.isCompatible();
                        logger.log("Version is compatible: " + isCompatible);
                        MinecraftExecutor executor = new MinecraftExecutor(Memory, width, height, jvm);
                        mcVersion.getInstaller().install(mcVersion, minecraftInstance, null);
                        List<String> command = mcVersion.getLauncher().getLaunchCommand(
                                session,
                                minecraftInstance,
                                mcVersion,
                                executor);

                        ProcessBuilder pb = new ProcessBuilder(command);
                        Process process = pb.start();

                        InputStream errorStream = process.getErrorStream();

                        new Thread(() -> {
                            try (BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(errorStream))) {
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    logger.log(line);
                                }
                            } catch (IOException e) {
                                logger.log(e.getMessage());
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    logger.log("Failed to fetch " + VanillaversionId + " version. ");
                }
            }
        };
        Thread thread = new Thread(
                new GetVanillaUrls(operation, VanillaversionId, minecraftInstance.getLocation().getAbsolutePath()));
        thread.start();
    }

}

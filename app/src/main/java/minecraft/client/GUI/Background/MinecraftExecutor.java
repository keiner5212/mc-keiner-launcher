package minecraft.client.GUI.Background;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import minecraft.client.GUI.Logger;

public class MinecraftExecutor {

    private String minecraftJar;
    private String libraries;
    private int maxMemory;
    private int minMemory;
    private String minecraftClass;
    private String username;
    private String version;
    private String gameDir;
    private String assetsDir;
    private String assetIndex;
    private String uuid;
    private String accessToken;
    private String clientId;
    private String xuid;
    private String userType;
    private String versionType;
    private int width;
    private int height;
    private String jvm;
    private Logger logger;

    public MinecraftExecutor(String minecraftJar, String libraries, int Memory, String minecraftClass,
            String username, String version, String gameDir, String assetsDir, String assetIndex, String uuid,
            String accessToken, String clientId, String xuid, String userType, String versionType, int width,
            int height, String jvm, Logger logger) {
        this.jvm = jvm;
        this.logger = logger;
        this.minecraftJar = minecraftJar;
        this.libraries = libraries;
        this.maxMemory = Memory;
        this.minMemory = Memory;
        this.minecraftClass = minecraftClass;
        this.username = username;
        this.version = version;
        this.gameDir = gameDir;
        this.assetsDir = assetsDir;
        this.assetIndex = assetIndex;
        this.uuid = uuid;
        this.accessToken = accessToken;
        this.clientId = clientId;
        this.xuid = xuid;
        this.userType = userType;
        this.versionType = versionType;
        this.width = width;
        this.height = height;
    }

    public void launchMinecraft() {
        try {
            List<String> command = new ArrayList<>();
            command.add(jvm);
            command.add("-Xmx" + maxMemory + "M");
            command.add("-Xms" + minMemory + "M");
            command.add("-XX:+UnlockExperimentalVMOptions");
            command.add("-XX:+UseG1GC");
            command.add("-XX:G1NewSizePercent=20");
            command.add("-XX:MaxGCPauseMillis=100");
            command.add("-XX:G1HeapRegionSize=16M");
            command.add("-Djava.net.preferIPv4Stack=true");
            command.add("-cp");
            command.add(minecraftJar + ";" + libraries);

            command.add(minecraftClass);
            command.add("--width");
            command.add(String.valueOf(width));
            command.add("--height");
            command.add(String.valueOf(height));
            command.add("--username");
            command.add(username);
            command.add("--version");
            command.add(version);
            command.add("--gameDir");
            command.add(gameDir);
            command.add("--assetsDir");
            command.add(assetsDir);
            command.add("--assetIndex");
            command.add(assetIndex);
            command.add("--uuid");
            command.add(uuid);
            command.add("--accessToken");
            command.add(accessToken);
            command.add("--clientId");
            command.add(clientId);
            command.add("--xuid");
            command.add(xuid);
            command.add("--userType");
            command.add(userType);
            command.add("--versionType");
            command.add(versionType);


            ProcessBuilder processBuilder = new ProcessBuilder(command);
            logger.log("Starting Minecraft...");
            logger.log("Command: " + processBuilder.command());
            Process process = processBuilder.start();

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

            System.exit(0);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}

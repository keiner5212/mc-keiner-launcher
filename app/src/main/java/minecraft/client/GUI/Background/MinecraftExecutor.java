package minecraft.client.GUI.Background;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

    public MinecraftExecutor(String minecraftJar, String libraries, int Memory, String minecraftClass,
            String username, String version, String gameDir, String assetsDir, String assetIndex, String uuid,
            String accessToken, String clientId, String xuid, String userType, String versionType, int width,
            int height) {
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
            command.add("java");
            command.add("-cp");
            command.add(minecraftJar + ":" + libraries);
            command.add("-Xmx" + maxMemory + "M");
            command.add("-Xms" + minMemory + "M");
            command.add("-XX:+UnlockExperimentalVMOptions");
            command.add("-XX:+UseG1GC");
            command.add("-XX:G1NewSizePercent=20");
            command.add("-XX:MaxGCPauseMillis=100");
            command.add("-XX:G1HeapRegionSize=16M");
            command.add("-Djava.net.preferIPv4Stack=true");
            command.add("--width " + width);
            command.add("--height " + height);
            command.add("--username " + username);
            command.add("--version " + version);
            command.add("--gameDir " + gameDir);
            command.add("--assetsDir " + assetsDir);
            command.add("--assetIndex " + assetIndex);
            command.add("--uuid " + uuid);
            command.add("--accessToken " + accessToken);
            command.add("--clientId " + clientId);
            command.add("--xuid " + xuid);
            command.add("--userType " + userType);
            command.add("--versionType " + versionType);
            command.add(minecraftClass);

            ProcessBuilder processBuilder = new ProcessBuilder(command);

            processBuilder.directory(new File(minecraftJar).getParentFile());

            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();

            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(errorStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.err.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            int exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}

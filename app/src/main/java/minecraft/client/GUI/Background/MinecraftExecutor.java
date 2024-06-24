package minecraft.client.GUI.Background;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import minecraft.client.api.common.ILaunchSettings;

public class MinecraftExecutor implements ILaunchSettings {

    private int maxMemory;
    private int minMemory;
    private int width;
    private int height;
    private String jvm;
    List<String> AdditionalcommandJVM;

    public MinecraftExecutor(int Memory, int width,
            int height, String jvm) {
        this.jvm = jvm;
        this.maxMemory = Memory;
        this.minMemory = Memory;
        this.width = width;
        this.height = height;
        this.AdditionalcommandJVM = new ArrayList<>();
        AdditionalcommandJVM.add("-XX:+UseG1GC");
        AdditionalcommandJVM.add("-XX:G1NewSizePercent=20");
        AdditionalcommandJVM.add("-XX:MaxGCPauseMillis=100");
        AdditionalcommandJVM.add("-XX:G1HeapRegionSize=16M");
        AdditionalcommandJVM.add("-Djava.net.preferIPv4Stack=true");
    }

    @Override
    public String getInitHeap() {
        return String.valueOf(minMemory + "M");
    }

    @Override
    public String getHeap() {
        return String.valueOf(maxMemory + "M");
    }

    @Override
    public Map<String, String> getCustomParameters() {
        return null;
    }

    @Override
    public List<String> getCommandPrefix() {
        return List.of();
    }

    @Override
    public boolean isModifyAppletOptions() {
        return false;
    }

    @Override
    public File getJavaLocation() {
        return new File(jvm);
    }

    @Override
    public List<String> getJavaArguments() {
        return AdditionalcommandJVM;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

}

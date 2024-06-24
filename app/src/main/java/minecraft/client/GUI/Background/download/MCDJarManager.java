package minecraft.client.GUI.Background.download;

import java.io.File;

import minecraft.client.api.common.mc.MinecraftInstance;
import minecraft.client.entities.versions.IVersion;

final class MCDJarManager {
    private final File versionsFolder;

    MCDJarManager(MinecraftInstance mc) {
        versionsFolder = new File(mc.getLocation(), "versions");
    }

    File getVersionFolder(IVersion version){
        return new File(versionsFolder, version.getId());
    }


    File getVersionJAR(MCDownloadVersion version) {
        return new File(new File(versionsFolder, version.getJarVersion()), version.getJarVersion() + ".jar");
    }

    File getInfoFile(IVersion version){
        return new File(getVersionFolder(version), version.getId() + ".json");
    }
}

package minecraft.client.impl.versions.mcassets;

import java.io.File;

import minecraft.client.api.common.mc.MinecraftInstance;
import minecraft.client.entities.versions.IVersion;

/**
 * JAR provider specifically for MCAssetsVersions.
 * Model:
 * Minecraft 1.4 jar location:  .minecraft/bin/1.4.jar
 * Natives location:            .minecraft/natives
 */
@Deprecated
final class MCAJarManager {
    private final File binDirectory;
    private final File nativesDirectory;

    MCAJarManager(MinecraftInstance mc){
        this.nativesDirectory = new File(mc.getLocation(), "natives");
        this.binDirectory = new File(mc.getLocation(), "bin");
    }

    File getVersionFile(IVersion version){
        return new File(binDirectory, version.getId()+".jar");
    }

    public File getNativesDirectory() {
        return nativesDirectory;
    }
}

package minecraft.client.impl.versions.mcassets;

import minecraft.client.entities.DummyProgressMonitor;
import minecraft.client.entities.versions.IVersion;
import minecraft.client.entities.versions.IVersionInstaller;
import minecraft.client.entities.versions.IVersionLauncher;

@Deprecated
final class MCAssetsVersion implements IVersion {
    private static final MCAssetsVersionInstaller installer = new MCAssetsVersionInstaller(DummyProgressMonitor.progressBar, DummyProgressMonitor.progressLabel);
    private static final MCAssetsVersionLauncher launcher = new MCAssetsVersionLauncher();
    private final String id;

    public MCAssetsVersion(String vid) {
        id = vid;
    }

    @Override
    public int compareTo(IVersion o) {
        return getId().compareTo(o.getId());
    }

    @Override
    public String getDisplayName() {
        String type = "release";
        if (MCAssetsVersionList.isSnapshot(this))
            type = "snapshot";
        return type.concat(" ").concat(getId());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUniqueID() {
        return getId();
    }

    @Override
    public IVersionInstaller getInstaller() {
        return installer;
    }

    @Override
    public IVersionLauncher getLauncher() {
        return launcher;
    }

    @Override
    public boolean isCompatible() {
        return true;
    }

    @Override
    public String getIncompatibilityReason() {
        return null;
    }
    
}

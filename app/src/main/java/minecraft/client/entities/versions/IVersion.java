package minecraft.client.entities.versions;
/**
 * General interface for versions
 *
 * @author keiner5212
 */
public interface IVersion extends Comparable<IVersion> {
    /**
     * @return Human-readable name of this version
     */
    public String getDisplayName();

    /**
     * @return ID of this version, like 1.7.5
     */
    public String getId();

    /**
     * @return Unique ID of this version, like s1.7.5 or r1.7.5
     */
    public String getUniqueID();

    /**
     * @return Installer that can install this version
     */
    public IVersionInstaller getInstaller();

    /**
     * @return Launcher that can run this version
     */
    public IVersionLauncher getLauncher();

    /**
     * @return True if this version is compatible with current runtime.
     */
    public boolean isCompatible();

    /**
     * @return Reason why it isn't compatible with specified runtime
     */
    public String getIncompatibilityReason();
}

package minecraft.client.entities.versions;

public interface IVersionInstallListener {
    /**
     * This method will be called when the version is installed
     *
     * @param version Newly installed version
     */
    public void versionInstalled(IVersion version);
}

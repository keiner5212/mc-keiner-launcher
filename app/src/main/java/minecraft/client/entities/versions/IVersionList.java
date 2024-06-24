package minecraft.client.entities.versions;

import minecraft.client.api.common.IObservable;

public interface IVersionList extends IObservable<String> {
    /**
     * Starts downloading list <b>on this thread</b>
     *
     * @throws Exception Network issues
     */
    public void startDownload() throws Exception;

    /**
     * Retrieves IVersion object with this ID
     * @param id ID of version to retrieve (e.g. "1.8")
     * @return IVersion object for the passed ID
     * @throws Exception - Network errors, JSON parsing errors, version inheritance problems
     */
    public IVersion retrieveVersionInfo(String id) throws Exception;

    /**
     *
     * @return LatestVersionInformation object
     * @throws Exception - Network errors, JSON parsing errors
     */
    public LatestVersionInformation getLatestVersionInformation() throws Exception;
}

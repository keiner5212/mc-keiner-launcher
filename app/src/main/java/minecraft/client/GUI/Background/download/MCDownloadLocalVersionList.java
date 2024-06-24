package minecraft.client.GUI.Background.download;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;

import minecraft.client.api.common.MCLauncherAPI;
import minecraft.client.api.common.mc.MinecraftInstance;
import minecraft.client.entities.versions.IVersion;
import minecraft.client.entities.versions.IVersionList;
import minecraft.client.entities.versions.LatestVersionInformation;
import minecraft.client.impl.common.Observable;

final class MCDownloadLocalVersionList extends Observable<String> implements IVersionList {
    private final File versionsFolder;

    public MCDownloadLocalVersionList(MinecraftInstance mc){
        versionsFolder = new File(mc.getLocation(), "versions");
    }

    private File getVersionFolder(String id){
        return new File(versionsFolder, id);
    }

    @Override
    public void startDownload() throws Exception {
        if(!versionsFolder.exists() || versionsFolder.isFile()){
            MCLauncherAPI.log.fine("'versions' folder at '".concat(versionsFolder.getAbsolutePath()).concat("' doesn't exist or is invalid."));
            return;
        }
        File[] versionFolders = versionsFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });
        for(File versionFolder : versionFolders){
            // do a quick check whether or not the needed JSON file exists
            File jsonFile = new File(versionFolder, versionFolder.getName().concat(".json"));
            if(jsonFile.exists()){
                notifyObservers(versionFolder.getName());
            }
        }
    }

    @Override
    public IVersion retrieveVersionInfo(String id) throws Exception {
        File versionFolder = getVersionFolder(id);
        if(!versionFolder.exists()){
            MCLauncherAPI.log.fine("version folder at '".concat(versionFolder.getAbsolutePath()).concat("' doesn't exist or is invalid."));
            return null;
        }
        File jsonFile = new File(versionFolder, versionFolder.getName().concat(".json"));
        FileReader fileReader = new FileReader(jsonFile);
        MCDownloadVersion result = MCDownloadVersion.fromJson((JSONObject) JSONValue.parse(fileReader));
        fileReader.close();
        return result;
    }

    @Override
    public LatestVersionInformation getLatestVersionInformation() throws Exception {
        /* for now, we'll return null. proper solution would be to list
         * all versions, compare their release time etc
         * but we won't be needing latest version from this source... */
        return null;
    }
}

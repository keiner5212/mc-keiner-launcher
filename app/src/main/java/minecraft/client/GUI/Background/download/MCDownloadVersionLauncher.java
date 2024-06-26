package minecraft.client.GUI.Background.download;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import minecraft.client.api.common.ILaunchSettings;
import minecraft.client.api.common.MCLauncherAPI;
import minecraft.client.api.common.mc.MinecraftInstance;
import minecraft.client.entities.ISession;
import minecraft.client.entities.ISession.Prop;
import minecraft.client.entities.versions.IVersion;
import minecraft.client.entities.versions.IVersionLauncher;
import minecraft.client.impl.common.Platform;
import minecraft.client.net.HttpRequests;
import minecraft.client.net.HttpUtils;
import minecraft.client.util.StringSubstitutor;

final class MCDownloadVersionLauncher implements IVersionLauncher {

    private List<String> getArguments(ArgumentList args, MinecraftInstance mc, File assetsDir,
            ISession session, ILaunchSettings settings,
            MCDownloadVersion version) {

        StringSubstitutor subst = new StringSubstitutor("${%s}");
        subst.setVariable("auth_session", session.getSessionID());
        subst.setVariable("auth_access_token", session.getSessionID());
        subst.setVariable("auth_player_name", session.getUsername());
        subst.setVariable("auth_uuid", session.getUUID());
        subst.setVariable("version_name", version.getId());
        subst.setVariable("game_directory", mc.getLocation().getAbsolutePath());
        subst.setVariable("game_assets", assetsDir
                .getAbsolutePath());
        subst.setVariable("assets_root", assetsDir
                .getAbsolutePath());
        subst.setVariable("assets_index_name", version.getAssetsIndexName());
        subst.setVariable("user_type", session.getType().toString()
                .toLowerCase());
        if (session.getProperties() != null
                && !session.getProperties().isEmpty()) {
            JSONObject propertiesObj = new JSONObject();
            List<Prop> properties = session.getProperties();
            for (Prop p : properties) {
                propertiesObj.put(p.name, p.value);
            }
            subst.setVariable("user_properties",
                    propertiesObj.toJSONString(JSONStyle.NO_COMPRESS));
        } else
            subst.setVariable("user_properties", "{}");

        List<String> result = new ArrayList<>();
        for (Argument arg : args) {
            if (arg.applies(Platform.getCurrentPlatform(), System.getProperty("os.version"), FeaturePreds.NONE)) {
                for (String val : arg.getValue()) {
                    result.add(subst.substitute(val));
                }
            }
        }

        return result;
    }

    @Override
    public List<String> getLaunchCommand(ISession session,
            MinecraftInstance mc, IVersion v,
            ILaunchSettings settings) throws Exception {

        boolean moddingProfileSpecified = false;
        MCDResourcesInstaller resourcesInstaller = new MCDResourcesInstaller(mc);
        MCDJarManager jarManager = new MCDJarManager(mc);
        LibraryProvider libraryProvider = new LibraryProvider(mc);
        // get local JSON information about the version
        File jsonFile = jarManager.getInfoFile(v);
        MCLauncherAPI.log.fine("Looking for ".concat(jsonFile.getAbsolutePath()));
        if (!jsonFile.exists()) {
            throw new FileNotFoundException(
                    "You need to download the version at first! (JSON description file not found!)");
        }
        // create MCDownloadVersion based on this version
        MCDownloadVersion version = (MCDownloadVersion) v;
        File jarFile = jarManager.getVersionJAR(version);
        // check if the version is compatible with our OS
        MCLauncherAPI.log.fine("Checking version compatibility...");
        if (!version.isCompatible()) {
            throw new VersionIncompatibleException(version);
        }
        MCLauncherAPI.log.fine("Checking version inheritance...");
        // check if everything's inherited
        if (version.needsInheritance())
            throw new VersionInheritanceException(version);
        MCLauncherAPI.log.fine("Checking minecraft launcher API version...");
        // check if we can launch it using the current version of MCLauncherAPI
        if (version.getMinimumLauncherVersion() > MCLauncherAPI.MC_LAUNCHER_VERSION) {
            throw new RuntimeException(
                    "You need to update MCLauncher-API to run this minecraft version! Required API version: "
                            + version.getMinimumLauncherVersion());
        }
        MCLauncherAPI.log.fine("Building the launch command...");
        // build the huge command!
        ArrayList<String> command = new ArrayList<String>();
        // prefix
        if (settings.getCommandPrefix() != null)
            command.addAll(settings.getCommandPrefix());
        // java location if entered or just "java"
        if (settings.getJavaLocation() != null)
            command.add(settings.getJavaLocation().getAbsolutePath());
        else
            command.add("java");
        // -Xms
        if (settings.getInitHeap() != null
                && settings.getInitHeap().length() > 0)
            command.add("-Xms".concat(settings.getInitHeap()));
        // -Xmx
        if (settings.getHeap() != null && settings.getHeap().length() > 0)
            command.add("-Xmx".concat(settings.getHeap()));
        // all additional java arguments
        if (settings.getJavaArguments() != null) {
            command.addAll(settings.getJavaArguments());
        }
        // find natives for this MC version
        final File nativesDir = new File(jarManager.getVersionFolder(v), "natives");
        command.add("-Djava.library.path=" + nativesDir.getAbsolutePath());
        // build classpath
        command.add("-cp");
        StringBuilder librariesString = new StringBuilder();
        final String LIBRARY_SEPARATOR = System.getProperty("path.separator");
        MCLauncherAPI.log.fine("Adding library files");
        //// now add library files
        for (Library lib : version.getLibraries()) {
            // each library has to be compatible, installed and allowed by modding profile
            if (lib.getArtifact() != null && lib.isCompatible() && (!moddingProfileSpecified)) {
                if (!libraryProvider.isInstalled(lib)) {
                    MCLauncherAPI.log.fine("Warning: Library " + lib.getName()
                            + " is not installed, trying to download from maven...");
                    boolean downloaded = HttpRequests.downloadFile(HttpUtils.getMavenJarUrl(lib.getName()),
                            mc.getLocation() + "\\libraries\\" + lib.getPath());
                    if (!downloaded)
                        throw new FileNotFoundException("Library file wasn't found: " + lib.getPath());
                }
                MCLauncherAPI.log.finest("Adding ".concat(lib.getName()));
                librariesString = librariesString.append(mc.getLocation() + "\\libraries\\" + lib.getPath())
                        .append(
                                LIBRARY_SEPARATOR);
            }
        }
        // append the game JAR at the end
        String jarToUse = jarFile.getAbsolutePath();
        librariesString = librariesString.append(jarToUse);
        // append the whole classpath to command
        command.add(librariesString.toString());

        // look for the main class
        String mainClass = version.getMainClass();
        command.add(mainClass);
        // create minecraft arguments
        List<String> arguments = getArguments(version.getGameArgs(), mc, resourcesInstaller.getAssetsDirectory(),
                session, settings,
                version);

        // size
        arguments.add("--width");
        arguments.add(String.valueOf(settings.getWidth()));
        arguments.add("--height");
        arguments.add(String.valueOf(settings.getHeight()));

        for (int i = 0; i < arguments.size(); i++) {
            String arg = arguments.get(i);
            if (arg.equalsIgnoreCase("${version_type}")) {
                arguments.set(i, version.getType());
            } else if (arg.equalsIgnoreCase("${auth_xuid}")) {
                arguments.set(i, "0000000000000000");
            } else if (arg.equalsIgnoreCase("${clientid}")) {
                arguments.set(i, "00000000-0000-0000-0000-000000000000:00000000-0000-0000-0000-000000000000");
            }
        }

        // append all arguments to the command
        for (String arg : arguments) {
            command.add(arg);
        }
        MCLauncherAPI.log.fine("Launching command is now ready.");
        MCLauncherAPI.log.fine(command.toString());
        return command;
    }
}

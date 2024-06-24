package minecraft.client.impl.versions.mcassets;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import minecraft.client.api.common.ILaunchSettings;
import minecraft.client.api.common.mc.MinecraftInstance;
import minecraft.client.entities.ISession;
import minecraft.client.entities.versions.IVersion;
import minecraft.client.entities.versions.IVersionLauncher;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

@Deprecated
public final class MCAssetsVersionLauncher implements IVersionLauncher {

    @Override
    public List<String> getLaunchCommand(ISession session, MinecraftInstance mc,  IVersion version, ILaunchSettings settings)
            throws Exception {
        MCAJarManager jarManager = new MCAJarManager(mc);
        // get path to this jar, so that we can relaunch
        String pathToJar = Relauncher.class.getProtectionDomain().getCodeSource().getLocation().getFile();

        List<String> command = new ArrayList<String>();
        // add command prefix from settings
        if (settings.getCommandPrefix() != null && !settings.getCommandPrefix().isEmpty())
            command.addAll(settings.getCommandPrefix());
        // setup java location if present
        if (settings.getJavaLocation() == null)
            command.add("java");
        else
            command.add(settings.getJavaLocation().getAbsolutePath());
        // add java arguments
        if (settings.getJavaArguments() != null && !settings.getJavaArguments().isEmpty())
            command.addAll(settings.getJavaArguments());
        // set memory for java
        command.add("-Xms".concat(settings.getInitHeap()));
        command.add("-Xmx".concat(settings.getHeap()));
        // setup classpath
        command.add("-cp");
        //// this jar
        command.add(pathToJar);
        command.add(Relauncher.class.getName());
        // parameters for relauncher:
        //// username
        command.add("-un");
        command.add(session.getUsername());
        //// session id
        command.add("-sid");
        command.add(session.getSessionID());
        //// game directory
        command.add("-dir");
        command.add(mc.getLocation().toString());
        //// game jar
        command.add("-jar");
        command.add(jarManager.getVersionFile(version).getPath());
        //// natives
        File[] files = MCAssetsVersionInstaller.getDefaultLWJGLJars(mc.getLocation());
        command.add("-lib");
        StringBuilder sb = new StringBuilder();
        for (File file : files) {
            sb = sb.append(file.getPath()).append(';');
        }
        if (sb.length() > 0)
            sb = sb.deleteCharAt(sb.length() - 1);
        command.add(sb.toString());
        //// custom parameters for relauncher
        if (settings.getCustomParameters() != null && settings.getCustomParameters().size() > 0) {
            JSONObject params = new JSONObject(settings.getCustomParameters());
            command.add("-args");
            command.add(params.toJSONString(JSONStyle.NO_COMPRESS));
        }
        
        //// modify applet options
        if (settings.isModifyAppletOptions()) {
            command.add("-ap");
            command.add("true");
        }
        //// native library path
        command.add("-lwjgl");
        command.add(jarManager.getNativesDirectory().getAbsolutePath());
        command.add("-jlibpath");
        command.add(jarManager.getNativesDirectory().getAbsolutePath());
        return command;
    }

}

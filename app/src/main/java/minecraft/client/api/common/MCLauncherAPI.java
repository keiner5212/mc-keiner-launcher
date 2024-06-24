package minecraft.client.api.common;


import java.util.logging.Logger;

import minecraft.client.impl.common.Platform;

public class MCLauncherAPI {
    public static final int MC_LAUNCHER_VERSION = 21;
    public static Logger log = Logger.getLogger(MCLauncherAPI.class.getName()); ;

    public MCLauncherAPI(Logger log) {
        MCLauncherAPI.log = log;
    }
    static {
        Platform.getCurrentPlatform();
    }
}

package minecraft.client.entities.versions;

import java.util.List;

import minecraft.client.api.common.ILaunchSettings;
import minecraft.client.api.common.mc.MinecraftInstance;
import minecraft.client.entities.ISession;

public interface IVersionLauncher {
    /**
     * Launches minecraft
     *
     * @param session  Login session
     * @param mc       Target minecraft instance
     * @param server   Server to connect to
     * @param version  Version to launch
     * @param settings Launch settings
     * @param mods     Mods to load along with minecraft
     * @return Process which was created
     * @throws Exception various errors
     */
    public List<String> getLaunchCommand(ISession session, MinecraftInstance mc, IVersion version, ILaunchSettings settings)
            throws Exception;

}

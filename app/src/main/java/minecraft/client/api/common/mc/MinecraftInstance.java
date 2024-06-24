package minecraft.client.api.common.mc;

import java.io.File;

/**
 * Class that holds information about Minecraft Folder
 *
 * @author keiner5212
 */
public class MinecraftInstance {
    private final File dir;

    public MinecraftInstance(File dir){
        this.dir = dir;
    }

    /**
     * @return Working directory .minecraft as {@link File} object
     */
    public final File getLocation(){
        return dir;
    }
}

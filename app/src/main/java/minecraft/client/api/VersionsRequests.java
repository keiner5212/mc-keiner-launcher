
package minecraft.client.api;

import java.util.HashMap;
import java.util.List;

import minecraft.client.entities.Json;
import minecraft.client.net.HttpRequests;

/**
 *
 * @author keiner5212
 */
@SuppressWarnings("unchecked")
public class VersionsRequests {
    private static final String VanillaversionsApiUrl = "https://launchermeta.mojang.com/mc/game/version_manifest_v2.json";
    private static final String FabricversionsApiUrl = "https://meta.fabricmc.net/v2/versions/loader";
    private static final String ForgeversionsApiUrl = "https://maven.minecraftforge.net/net/minecraftforge/forge/maven-metadata.xml";

    public static List<HashMap<String, Object>> getVersionsVanilla() {
        Json json = HttpRequests.sendGetJSONHTTPRequest(VanillaversionsApiUrl, true);
        return ((List<HashMap<String, Object>>) json.getContent().get("versions"));
    }

    public static List<HashMap<String, Object>> getVersionsFabric() {
        Json json = HttpRequests.sendGetJSONHTTPRequest(FabricversionsApiUrl, true);
        return ((List<HashMap<String, Object>>) json.getContent().get("data"));
    }

    public static List<String> getVersionsForge() {
        Json json = HttpRequests.sendGetXMLHTTPRequest(ForgeversionsApiUrl, true);
        HashMap<String, Object> versioning= (HashMap<String, Object>) json.getContent().get("versioning");
        HashMap<String, Object> versions = (HashMap<String, Object>) versioning.get("versions");
        return (List<String>) versions.get("version");
    }
}

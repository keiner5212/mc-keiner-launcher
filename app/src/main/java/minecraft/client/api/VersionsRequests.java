
package minecraft.client.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import minecraft.client.net.HttpRequests;
import minecraft.client.util.JsonUtils;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 *
 * @author keiner5212
 */
public class VersionsRequests {
    private static final String VanillaversionsApiUrl = "https://launchermeta.mojang.com/mc/game/version_manifest_v2.json";
    // private static final String FabricversionsApiUrl = "https://meta.fabricmc.net/v2/versions/loader";
    private static final String ForgeversionsApiUrl = "https://maven.minecraftforge.net/net/minecraftforge/forge/maven-metadata.xml";

    public static JSONArray getVersionsVanilla() {
        JSONObject json = HttpRequests.sendGetJSONHTTPRequest(VanillaversionsApiUrl, true);
        return (JSONArray) json.get("versions");
    }

    // public static JSONObject getVersionsFabric() {
    //     JSONObject json = HttpRequests.sendGetJSONHTTPRequest(FabricversionsApiUrl, true);
    //     System.out.println(json);
    //     return null;
    // }

    @SuppressWarnings("unchecked")
    public static List<String> getVersionsForge() {
        JSONObject json = HttpRequests.sendGetXMLHTTPRequest(ForgeversionsApiUrl, true);
        Object versioning =json.get("versioning");
        Object versions = JsonUtils.JSONFromObject(versioning).get("versions");
        Object version = JsonUtils.JSONFromObject(versions).get("version");
        return (List<String>) version;
    }
}

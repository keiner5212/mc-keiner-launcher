package minecraft.client.api;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import minecraft.client.entities.Json;
import minecraft.client.entities.Operation;
import minecraft.client.net.HttpRequests;
import minecraft.client.persistance.FileManager;

public class GetVanillaUrls implements Runnable {
    private Operation op;
    private String version;
    private String Path;

    public GetVanillaUrls(Operation op, String version, String Path) {
        this.Path = Path;
        this.op = op;
        this.version = version;
    }

    @Override
    public void run() {
        try {
            JSONObject jo = FileManager.loadData(Path + "\\versions\\" + version + "\\" + version + ".json");
            op.Run(Json.fromJSONObject(jo));
        } catch (Exception e) {
            ArrayList<HashMap<String, Object>> versions = (ArrayList<HashMap<String, Object>>) VersionsRequests
                    .getVersionsVanilla();
            String url = null;
            boolean found = false;
            for (HashMap<String, Object> v : versions) {
                if (v.get("id").toString().equals(version)) {
                    found = true;
                    url = (v.get("url").toString());
                }
            }

            if (!found) {
                op.Run();
            } else {
                Json json = HttpRequests.sendGetJSONHTTPRequest(url, true);
                op.Run(json);
            }
        }
    }
}

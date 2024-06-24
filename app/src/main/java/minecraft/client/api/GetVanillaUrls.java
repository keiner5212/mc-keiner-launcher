package minecraft.client.api;

import java.util.ArrayList;
import java.util.HashMap;

import minecraft.client.entities.Operation;
import minecraft.client.net.HttpRequests;
import minecraft.client.persistance.FileManager;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

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
        JSONObject jo = null;
        jo = FileManager.loadData(Path + "\\versions\\" + version + "\\" + version + ".json");
        if (jo == null) {
            JSONArray versions = VersionsRequests
                    .getVersionsVanilla();
            String url = null;
            boolean found = false;
            for (Object elem : versions) {
                JSONObject v = (JSONObject) elem;
                if (v.get("id").toString().equals(version)) {
                    found = true;
                    url = (v.get("url").toString());
                }
            }

            if (!found) {
                op.Run();
            } else {
                JSONObject json = HttpRequests.sendGetJSONHTTPRequest(url, true);
                op.Run(json);
            }

        } else {
            op.Run(jo);
        }

    }
}

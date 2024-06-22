package minecraft.client.api;

import java.util.ArrayList;
import java.util.HashMap;

import minecraft.client.entities.Json;
import minecraft.client.entities.Operation;
import minecraft.client.net.HttpRequests;

public class GetVanillaUrls implements Runnable {
    private Operation op;
    private String version;

    public GetVanillaUrls(Operation op, String version) {
        this.op = op;
        this.version = version;
    }

    @Override
    public void run() {
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

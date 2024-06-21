package minecraft.client;

import java.util.List;

import minecraft.client.entities.Json;
import minecraft.client.net.HttpRequests;

public class App {
    public static void main(String[] args) {
        Json json = HttpRequests.sendGetJSONHTTPRequest("https://launchermeta.mojang.com/mc/game/version_manifest.json");
        System.out.println(((List) json.getContent().get("versions")).get(0));
    }
}

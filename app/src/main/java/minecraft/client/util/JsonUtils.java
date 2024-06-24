package minecraft.client.util;

import java.util.HashMap;

import net.minidev.json.JSONObject;

public class JsonUtils {
    public static JSONObject JSONFromObject(Object object) {
        JSONObject parsed = new JSONObject((HashMap<String, Object>) object);
        return parsed;
    }
}

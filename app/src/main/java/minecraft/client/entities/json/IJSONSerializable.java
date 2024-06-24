package minecraft.client.entities.json;

import net.minidev.json.JSONObject;

public interface IJSONSerializable {
    /**
     * @return JSONObject which represents this object.
     */
    public JSONObject toJSON();
}

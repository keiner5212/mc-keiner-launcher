package minecraft.client.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Json {
    private Map<String, Object> content = new HashMap<>();

    public Map<String, Object> getContent() {
        return content;
    }

    public static Json JsonfromHttpEntity(HttpEntity entity, boolean isXml) {
        Json json = new Json();
        try {
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                ObjectMapper mapper = isXml ? new XmlMapper() : new ObjectMapper();
                JsonNode rootNode = mapper.readTree(result);
                if (rootNode.isArray()) {
                    ArrayList<Object> list = new ArrayList<>();
                    json.parseJsonArray(rootNode, list);
                    json.getContent().put("data", list);
                } else {
                    json.parseJsonNode(rootNode, json.getContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    private void parseJsonNode(JsonNode node, Map<String, Object> map) {
        if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                String key = entry.getKey();
                JsonNode value = entry.getValue();
                if (value.isValueNode()) {
                    map.put(key, value.asText());
                } else if (value.isArray()) {
                    List<Object> list = new ArrayList<>();
                    for (JsonNode arrayElement : value) {
                        if (arrayElement.isObject()) {
                            Map<String, Object> nestedMap = new HashMap<>();
                            parseJsonNode(arrayElement, nestedMap);
                            list.add(nestedMap);
                        } else if (arrayElement.isValueNode()) {
                            list.add(arrayElement.asText());
                        } else {
                            list.add(arrayElement);
                        }
                    }
                    map.put(key, list);
                } else if (value.isObject()) {
                    Map<String, Object> nestedMap = new HashMap<>();
                    parseJsonNode(value, nestedMap);
                    map.put(key, nestedMap);
                }
            });
        }
    }

    private void parseJsonArray(JsonNode arrayNode, List<Object> list) {
        for (JsonNode arrayElement : arrayNode) {
            if (arrayElement.isObject()) {
                Map<String, Object> nestedMap = new HashMap<>();
                parseJsonNode(arrayElement, nestedMap);
                list.add(nestedMap);
            } else if (arrayElement.isValueNode()) {
                list.add(arrayElement.asText());
            } else if (arrayElement.isArray()) {
                List<Object> nestedList = new ArrayList<>();
                parseJsonArray(arrayElement, nestedList);
                list.add(nestedList);
            }
        }
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }
}

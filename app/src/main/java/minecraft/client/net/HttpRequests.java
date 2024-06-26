package minecraft.client.net;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;


public class HttpRequests {
    private static HashMap<String, JSONObject> cache = new HashMap<>();

    public static JSONObject sendGetJSONHTTPRequest(String url, boolean useCache) {
        if (useCache && cache.containsKey(url)) {
            return cache.get(url);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    JSONObject parsed =  (JSONObject) JSONValue.parse(result);
                    if (useCache) {
                        cache.put(url, parsed);
                    }
                    return parsed;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JSONObject sendGetXMLHTTPRequest(String url, boolean useCache) {
        if (useCache && cache.containsKey(url)) {
            return cache.get(url);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    
                    // Use Jackson to convert XML to a generic Object
                    ObjectMapper xmlMapper = new XmlMapper();
                    Object xmlObject = xmlMapper.readValue(result, Object.class);
                    
                    // Convert the generic Object to net.minidev.json.JSONObject
                    @SuppressWarnings("unchecked")
                    JSONObject parsed = new JSONObject((HashMap<String, Object>) xmlObject);
                    
                    if (useCache) {
                        cache.put(url, parsed);
                    }
                    return parsed;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean downloadFile(String url, String savePath) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    try (InputStream inputStream = entity.getContent()) {
                        Path outputPath = Paths.get(savePath);
                        Files.createDirectories(outputPath.getParent());
                        try (OutputStream outputStream = new FileOutputStream(outputPath.toFile())) {
                            byte[] buffer = new byte[8192];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }
                        }
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}

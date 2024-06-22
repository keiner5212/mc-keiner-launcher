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

import minecraft.client.entities.Json;

public class HttpRequests {
    private static HashMap<String, Json> cache = new HashMap<>();

    public static Json sendGetJSONHTTPRequest(String url, boolean useCache) {
        if (useCache && cache.containsKey(url)) {
            return cache.get(url);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                Json parsed = Json.JsonfromHttpEntity(entity, false);
                if (useCache) {
                    cache.put(url, parsed);
                }
                return parsed;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Json sendGetXMLHTTPRequest(String url, boolean useCache) {
        if (useCache && cache.containsKey(url)) {
            return cache.get(url);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                Json parsed = Json.JsonfromHttpEntity(entity, true);
                if (useCache) {
                    cache.put(url, parsed);
                }
                return parsed;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean downloadJarFile(String url, String savePath) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    try (InputStream inputStream = entity.getContent()) {
                        Path outputPath = Paths.get(savePath);
                        Files.createDirectories(outputPath.getParent());
                        try (OutputStream outputStream = new FileOutputStream(outputPath.toFile())) {
                            byte[] buffer = new byte[4096];
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

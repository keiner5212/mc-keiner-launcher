package minecraft.client.net;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import minecraft.client.entities.Json;

public class HttpRequests {

    public static Json sendGetJSONHTTPRequest(String url) {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                return Json.JsonfromHttpEntity(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}

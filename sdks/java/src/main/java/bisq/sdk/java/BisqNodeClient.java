package bisq.sdk.java;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class BisqNodeClient {

    public String fetchVersion() {
        var infoEndpoint = "http://localhost:2141/info";
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(infoEndpoint))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            ObjectMapper mapper = new ObjectMapper();
            //noinspection unchecked
            Map<String, String> responseMap = mapper.readValue(responseBody, Map.class);
            return responseMap.get("version");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

package bisq.app.cli;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class BisqCli {
    public static void main(String... args) {
        System.out.println("Bisq X client version v2.1.0");
        fetchVersion();
    }

    private static void fetchVersion() {
        var infoEndpoint = "http://localhost:2141/info";
        System.out.println("Calling " + infoEndpoint);
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(infoEndpoint))
                    .GET()
                    .build();
            for (int i = 0; i < 1; i++) {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();
                ObjectMapper mapper = new ObjectMapper();
                //noinspection unchecked
                Map<String, String> responseMap = mapper.readValue(responseBody, Map.class);
                System.out.println("Remote Bisq node version: " + responseMap.get("version"));
            }
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.exit(1);
        }
    }
}

package bisq.demo.bot;

import bisq.client.oas.ApiClient;
import bisq.client.oas.Configuration;

import java.time.Duration;

public class BisqDcaDemo {

    public static void main(String... args) {
        System.out.println("Bisq DCA bot demo");

        ApiClient bisqClient = Configuration.getDefaultApiClient();
        bisqClient.setBasePath("http://localhost:2141");

        var nodeVersion = "0.0.0";
        System.out.println("Connected to node version " + nodeVersion);
        System.out.println("Placing offers to buy every 10 seconds until killed");
        while (true) {
            try {
                Thread.sleep(Duration.ofSeconds(10));
                System.out.println("Placing buy offer");
            } catch (InterruptedException ex) {
                System.err.println("Error: " + ex.getMessage());
                System.exit(1);
            }
        }
    }
}

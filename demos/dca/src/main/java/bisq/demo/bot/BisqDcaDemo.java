package bisq.demo.bot;

import bisq.client.oas.ApiClient;
import bisq.client.oas.ApiException;
import bisq.client.oas.Configuration;
import bisq.client.oas.endpoint.InfoEndpoint;
import bisq.client.oas.endpoint.OfferEndpoint;
import bisq.client.oas.model.AddRequest;
import bisq.client.oas.model.Offer;

import java.time.Duration;

public class BisqDcaDemo {

    public static void main(String... args) throws ApiException {
        System.out.println("Bisq DCA bot demo");

        ApiClient bisqClient = Configuration.getDefaultApiClient();
        bisqClient.setBasePath("http://localhost:2141");

        var info = new InfoEndpoint(bisqClient).getInfo();
        var offers = new OfferEndpoint(bisqClient);
        var interval = Duration.ofSeconds(2);

        System.out.println("Connected to node version " + info.getVersion());
        System.out.printf("Placing buy offers every %s until killed\n", interval);
        while (true) {
            try {
                Offer offer = new Offer().details("BLAH");
                System.out.println("Placing buy offer: " + offer);
                offers.add(new AddRequest().offer(offer));
                Thread.sleep(interval);
            } catch (InterruptedException ex) {
                System.err.println("Error: " + ex.getMessage());
                System.exit(1);
            }
        }
    }
}

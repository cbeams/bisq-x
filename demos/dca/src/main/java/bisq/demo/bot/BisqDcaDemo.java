/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package bisq.demo.bot;

import bisq.client.oas.ApiClient;
import bisq.client.oas.ApiException;
import bisq.client.oas.Configuration;
import bisq.client.oas.endpoint.InfoEndpoint;
import bisq.client.oas.endpoint.OfferbookEndpoint;
import bisq.client.oas.model.AddOfferRequest;
import bisq.client.oas.model.Offer;

import java.time.Duration;

public class BisqDcaDemo {

    public static void main(String... args) throws ApiException {
        System.out.println("Bisq DCA bot demo");

        ApiClient bisqClient = Configuration.getDefaultApiClient();
        bisqClient.setBasePath("http://localhost:2141");

        var info = new InfoEndpoint(bisqClient).getInfo();
        var offerbook = new OfferbookEndpoint(bisqClient);
        var interval = Duration.ofSeconds(2);

        System.out.println("Connected to node version " + info.getVersion());
        System.out.printf("Placing buy offers every %s until killed\n", interval);
        while (true) {
            try {
                Offer offer = new Offer().details("BLAH");
                System.out.println("Placing buy offer: " + offer);
                offerbook.addOffer(new AddOfferRequest().offer(offer));
                Thread.sleep(interval);
            } catch (InterruptedException ex) {
                System.err.println("Error: " + ex.getMessage());
                System.exit(1);
            }
        }
    }
}

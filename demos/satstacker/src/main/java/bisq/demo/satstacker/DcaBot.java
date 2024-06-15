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

package bisq.demo.satstacker;

import bisq.client.java.infrastructure.ApiClient;
import bisq.client.java.infrastructure.ApiException;
import bisq.client.java.infrastructure.Configuration;
import bisq.client.java.models.AddOfferRequest;
import bisq.client.java.models.Offer;
import bisq.client.java.operations.NodeInfoOperations;
import bisq.client.java.operations.OfferbookOperations;

import java.time.Duration;

public class DcaBot {

    public static void main(String... args) throws ApiException {
        System.out.println("Bisq DCA bot demo");

        ApiClient bisqClient = Configuration.getDefaultApiClient();
        bisqClient.setBasePath("http://localhost:2141");

        var info = new NodeInfoOperations(bisqClient).getNodeInfo();
        var offerbook = new OfferbookOperations(bisqClient);
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

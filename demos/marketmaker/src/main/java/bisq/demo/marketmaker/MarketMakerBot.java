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

package bisq.demo.marketmaker;

import bisq.client.java.ApiClient;
import bisq.client.java.ApiException;
import bisq.client.java.Configuration;
import bisq.client.java.operations.InfoOperations;

import java.time.Duration;

public class MarketMakerBot {

    public static void main(String... args) throws ApiException {
        System.out.println("Placeholder Bisq market maker bot demo");

        ApiClient bisqClient = Configuration.getDefaultApiClient();
        bisqClient.setBasePath("http://localhost:2141");

        var info = new InfoOperations(bisqClient).getInfo();
        var interval = Duration.ofSeconds(2);

        System.out.println("Connected to node version " + info.getVersion());
    }
}

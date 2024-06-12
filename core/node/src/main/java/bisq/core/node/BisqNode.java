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

package bisq.core.node;

import bisq.core.api.ApiController;
import bisq.core.api.ApiLayer;
import bisq.core.domain.trade.Offerbook;
import bisq.core.logging.Logging;
import bisq.core.network.http.HttpServer;
import bisq.core.network.p2p.P2PServer;
import bisq.core.oas.OpenApiSpecification;

import ch.qos.logback.classic.Level;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.Map;

public class BisqNode {

    private static final Logger log = NodeAssembly.log;

    private final Options options;
    private final P2PServer p2pServer;
    private final HttpServer httpServer;
    private final Collection<ApiController> apiControllers;
    private final Offerbook offerbook;

    private DataDir dataDir;

    public BisqNode(Options options,
                    P2PServer p2pServer,
                    HttpServer httpServer,
                    Collection<ApiController> apiControllers,
                    Offerbook offerbook,
                    // injected to express dependency from core.node => core.oas
                    @SuppressWarnings("unused") OpenApiSpecification openApiSpecification) {
        this.options = options;
        this.p2pServer = p2pServer;
        this.httpServer = httpServer;
        this.apiControllers = apiControllers;
        this.offerbook = offerbook;
    }

    public static BisqNode withOptions(Options options) {

        var context = ApplicationContext.builder()
                .properties(Map.of("micronaut.server.port", options.httpPort()))
                .start();

        var p2pServer = new P2PServer(options.p2pPort());
        var httpServer = new HttpServer(context.getBean(EmbeddedServer.class));
        var apiControllers = context.getBeansOfType(ApiController.class);
        var offerbook = context.getBean(Offerbook.class);
        var openApiSpec = context.getBean(OpenApiSpecification.class);

        return new BisqNode(
                options,
                p2pServer,
                httpServer,
                apiControllers,
                offerbook,
                openApiSpec
        );
    }

    public void start() {
        log.info("Starting up");

        // Enable debug logging
        if (options.debug())
            Logging.setLevel(Level.DEBUG);

        // Init data directory
        dataDir = DataDir.init(options);

        // Start services
        log.debug("Starting all services");
        p2pServer.start();
        httpServer.start();

        // Report available API endpoints
        ApiLayer.log.debug("Reporting available api endpoints");
        apiControllers.forEach(controller -> controller.logEndpoints(ApiLayer.log));

        // Register shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutdown requested");
            this.shutdown();
        }));

        log.info("Startup complete");
    }

    public void shutdown() {
        log.info("Shutdown in progress ...");
        p2pServer._stop();
        httpServer.stop();
        dataDir.close();
        log.info("Shutdown complete");
    }

    public String getName() {
        return options.appName();
    }

    public Offerbook getOfferbook() {
        return offerbook;
    }
}

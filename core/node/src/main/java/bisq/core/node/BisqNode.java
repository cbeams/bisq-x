package bisq.core.node;

import bisq.core.api.ApiController;
import bisq.core.domain.trade.OfferRepository;
import bisq.core.network.http.HttpServer;
import bisq.core.network.p2p.P2PServer;
import bisq.core.oas.OpenApiSpecification;
import bisq.core.util.logging.Logging;
import org.slf4j.Logger;

import java.util.Collection;

public class BisqNode implements Runnable {

    private static final String NODE_LOG_NAME = "node";
    private static final Logger log = Logging.getLog(NODE_LOG_NAME);

    private final Options options;
    private final OfferRepository offerRepository;
    private final HttpServer httpServer;
    private final Collection<ApiController> apiControllers;

    BisqNode(Options options,
             OfferRepository offerRepository,
             HttpServer httpServer,
             Collection<ApiController> apiControllers,
             // injected to express dependency from core.node => core.oas
             @SuppressWarnings("unused") OpenApiSpecification openApiSpecification) {
        this.options = options;
        this.offerRepository = offerRepository;
        this.httpServer = httpServer;
        this.apiControllers = apiControllers;
    }

    public static BisqNode withOptions(Options options) {
        return BisqNodeFactory.buildWithOptions(options);
    }

    @Override
    public void run() {

        log.info("Starting up");

        // Init data dir
        if (!options.dataDir().exists()) {
            log.info("Creating data directory {}", options.dataDir());
            //noinspection ResultOfMethodCallIgnored
            options.dataDir().mkdirs();
        }

        // Start services
        log.debug("Starting all services");
        var p2pService = new P2PServer(options.p2pPort());
        p2pService.start();
        httpServer.run();

        log.debug("Reporting available api controllers");
        apiControllers.forEach(ApiController::report);

        // Register shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Receiving shutdown signal");
            log.info("Shutting down");
        }));
    }

    public OfferRepository getOfferRepository() {
        return offerRepository;
    }
}

package bisq.core.node;

import bisq.core.api.ApiController;
import bisq.core.logging.ApiCategory;
import bisq.core.oas.OpenApiSpecification;

import bisq.core.network.http.HttpServer;
import bisq.core.network.p2p.PeerService;

import bisq.core.domain.trade.OfferRepository;

import bisq.core.logging.Logging;
import ch.qos.logback.classic.Level;
import org.slf4j.Logger;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;

import java.util.Collection;
import java.util.Map;

public class BisqNode {

    private static final Logger log = NodeCategory.log;

    private final Options options;
    private final PeerService peerService;
    private final HttpServer httpServer;
    private final Collection<ApiController> apiControllers;
    private final OfferRepository offerRepository;

    private DataDir dataDir;

    public BisqNode(Options options,
                    PeerService peerService,
                    HttpServer httpServer,
                    Collection<ApiController> apiControllers,
                    OfferRepository offerRepository,
                    // injected to express dependency from core.node => core.oas
                    @SuppressWarnings("unused") OpenApiSpecification openApiSpecification) {
        this.options = options;
        this.peerService = peerService;
        this.httpServer = httpServer;
        this.apiControllers = apiControllers;
        this.offerRepository = offerRepository;
    }

    public static BisqNode withOptions(Options options) {

        var context = ApplicationContext.builder()
                .properties(Map.of("micronaut.server.port", options.httpPort()))
                .start();

        var p2pServer = new PeerService(options.p2pPort());
        var httpServer = new HttpServer(context.getBean(EmbeddedServer.class));
        var apiControllers = context.getBeansOfType(ApiController.class);
        var offerRepository = context.getBean(OfferRepository.class);
        var openApiSpec = context.getBean(OpenApiSpecification.class);

        return new BisqNode(
                options,
                p2pServer,
                httpServer,
                apiControllers,
                offerRepository,
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
        peerService.start();
        httpServer.start();

        // Report available API endpoints
        ApiCategory.log.debug("Reporting available api endpoints");
        apiControllers.forEach(controller -> controller.logEndpoints(ApiCategory.log));

        // Register shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutdown requested");
            this.shutdown();
        }));

        log.info("Startup complete");
    }

    public void shutdown() {
        log.info("Shutdown in progress ...");
        peerService.stop();
        httpServer.stop();
        dataDir.close();
        log.info("Shutdown complete");
    }

    public String getName() {
        return options.appName();
    }

    public OfferRepository getOfferRepository() {
        return offerRepository;
    }
}

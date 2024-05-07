package bisq.core.node;

import bisq.core.node.api.RestApiService;
import bisq.core.p2p.P2PService;
import bisq.core.util.Logging;
import org.slf4j.Logger;

public class BisqNode implements Runnable {

    private static final Logger log = Logging.nodeLog;

    private final Options options;

    public BisqNode(Options options) {
        this.options = options;
    }

    @Override
    public void run() {

        log.info("Starting up");

        // Init data dir
        if (!options.dataDir.exists()) {
            log.info("Creating data directory {}", options.dataDir);
            //noinspection ResultOfMethodCallIgnored
            options.dataDir.mkdirs();
        }

        // Start services
        log.debug("Starting all services");
        var p2pService = new P2PService(options.p2pPort);
        p2pService.start();
        new RestApiService(options.apiPort).run();

        // Register shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Receiving shutdown signal");
            log.info("Shutting down");
        }));
    }
}

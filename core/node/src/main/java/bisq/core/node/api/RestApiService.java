package bisq.core.node.api;

import bisq.core.util.Logging;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;

import org.slf4j.Logger;

import java.util.Map;

public class RestApiService implements Runnable {

    private static final Logger log = Logging.apiLog;

    private final int port;

    public RestApiService(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        String[] args = new String[]{};
        ApplicationContext ctx = BisqMicronaut.build(args)
                .banner(false)
                .properties(Map.of("micronaut.server.port", port))
                .start();
        var server = ctx.getBean(EmbeddedServer.class);
        log.info("Listening for client requests at http://{}:{}", server.getHost(), server.getPort());
        log.info("Serving interactive api docs at http://{}:{}/swagger-ui", server.getHost(), server.getPort());
    }
}

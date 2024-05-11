package bisq.core.network.http;

import bisq.core.util.logging.Logging;
import io.micronaut.runtime.server.EmbeddedServer;
import org.slf4j.Logger;

import jakarta.inject.Singleton;

@Singleton
public class HttpServer implements Runnable {

    private static final Logger log = Logging.httpLog;
    private final EmbeddedServer httpServer;

    public HttpServer(EmbeddedServer httpServer) {
        this.httpServer = httpServer;
    }

    @Override
    public void run() {
        // We're faking it a little bit here, as the EmbeddedServer is actually already
        // running by the time it was injected at construction time. Nevertheless, we defer
        // printing this status to the log until this dummy method is called. Ideally it
        // would be possible to configure Micronaut to avoid calling EmbeddedServer.start()
        // and allow us to do it here, but after some digging into the code it wasn't
        // obvious that this is possible.
        log.info("Listening for client requests at http://localhost:{}", httpServer.getPort());
        log.info("Serving interactive api docs at http://localhost:{}/swagger-ui", httpServer.getPort());
    }
}
package bisq.core.network.http;

import io.micronaut.runtime.server.EmbeddedServer;

import java.io.Closeable;
import java.net.URI;

import static bisq.core.network.http.HttpLog.log;

public class HttpServer implements Runnable, Closeable {

    private final URI baseURL;
    private final EmbeddedServer server;

    public HttpServer(EmbeddedServer httpServer) {
        this.server = httpServer;
        this.baseURL = URI.create("http://localhost:" + httpServer.getPort());
    }

    public void start() {
        if (server.isRunning())
            throw new IllegalStateException("http server is already running");
        server.start();
        log.info("Listening for requests at {}", getBaseURL());
        log.info("Serving interactive api docs at {}/swagger-ui", getBaseURL());
    }

    public void stop() {
        if (server.isRunning()) {
            log.info("Stopping http server");
            server.stop();
        }
    }

    @Override
    public void run() {
        this.start();
    }

    @Override
    public void close() {
        this.stop();
    }

    public URI getBaseURL() {
        return baseURL;
    }
}

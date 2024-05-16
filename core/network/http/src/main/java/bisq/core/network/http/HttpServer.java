package bisq.core.network.http;

import io.micronaut.runtime.server.EmbeddedServer;

import java.net.URI;
import jakarta.inject.Singleton;

import static bisq.core.network.http.HttpLog.log;

@Singleton
public class HttpServer implements Runnable {

    private final URI baseURL;

    public HttpServer(EmbeddedServer httpServer) {
        this.baseURL = URI.create("http://localhost:" + httpServer.getPort());
    }

    @Override
    public void run() {
        // We're faking it a little bit here, as the EmbeddedServer is actually already
        // running by the time it was injected at construction time. Nevertheless, we defer
        // printing this status to the log until this dummy method is called. Ideally it
        // would be possible to configure Micronaut to avoid calling EmbeddedServer.start()
        // and allow us to do it here, but after some digging into the code it wasn't
        // obvious that this is possible.
        log.info("Listening for client requests at {}", getBaseURL());
        log.info("Serving interactive api docs at {}/swagger-ui", getBaseURL());
    }

    public URI getBaseURL() {
        return baseURL;
    }

    public URI resolveURL(String path) {
        return URI.create(getBaseURL() + path);
    }
}

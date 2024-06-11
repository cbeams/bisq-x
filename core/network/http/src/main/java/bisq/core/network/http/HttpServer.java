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

package bisq.core.network.http;

import io.micronaut.runtime.server.EmbeddedServer;

import java.io.Closeable;
import java.net.URI;

import static bisq.core.network.http.HttpCategory.log;

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

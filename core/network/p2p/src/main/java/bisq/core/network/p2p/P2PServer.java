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

package bisq.core.network.p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;

import static bisq.core.network.p2p.P2PSubsystem.log;

public class P2PServer extends Thread {

    private final int port;

    public P2PServer(int port) {
        this.port = port;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Listening for peer connections at bisq://localhost:{}", port);

            //noinspection InfiniteLoopStatement
            while (true) {
                Socket socket = serverSocket.accept();
                log.debug("New client connected");

                // Handle each client in a new thread
                new ClientHandler(socket).start();
            }

        } catch (IOException ex) {
            log.error("Error: {}", ex.getMessage());
            int status = 1;
            log.info("Exiting with status {}", status);
            System.exit(status);
        }
    }

    public void _stop() {
        log.info("Stopping p2p service");
        this.interrupt();
    }

    // Thread class to handle each client connection
    private static class ClientHandler extends Thread {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (InputStream input = socket.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                 OutputStream output = socket.getOutputStream();
                 PrintWriter writer = new PrintWriter(output, true)) {

                String text;

                // Read client input until "bye" is received
                while ((text = reader.readLine()) != null) {
                    log.info("Received: {}", text);
                    writer.println("Echo: " + text);

                    if ("bye".equalsIgnoreCase(text)) {
                        log.info("Client terminated connection");
                        break;
                    }
                }

                socket.close();

            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
    }
}

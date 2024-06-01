package bisq.core.network.p2p;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static bisq.core.network.p2p.P2PCategory.log;

public class P2PService implements Runnable {

    private final int port;

    public P2PService(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newCachedThreadPool();
        P2PServer server = new P2PServer("localhost", port);
        var localAddr = server.getAddress();
        executor.submit(server);

        if (port != 2140) {
            var seedAddr = "localhost:2140";
            try {
                var client = new P2PClient(seedAddr, localAddr);
                var ret = client.getPeers();
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
    }

    public void start() {
        run();
    }

    public void stop() {
        log.info("Stopping p2p server");
    }
}
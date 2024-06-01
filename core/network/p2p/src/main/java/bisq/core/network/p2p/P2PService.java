package bisq.core.network.p2p;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static bisq.core.network.p2p.P2PCategory.log;

public class P2PService implements Runnable {

    private final int port;
    private final Set<String> knownPeers = new HashSet<>();
    private final Map<String, P2PClient> connections = new HashMap<>();

    public P2PService(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newCachedThreadPool();
        P2PServer server = new P2PServer("localhost", port);
        var localAddr = server.getAddress();
        executor.submit(server);

        if (port != 2140)
            knownPeers.add("localhost:2140"); // seed node 1

        for (String addr : knownPeers) {
            try {
                var client = new P2PClient(addr, localAddr);
                connections.put(addr, client);
                //var ret = client.getPeers();
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
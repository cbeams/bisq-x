package bisq.core.network.p2p;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static bisq.core.network.p2p.P2PCategory.log;

public class P2PService implements Runnable {

    private final int port;
    private final PeerDirectory peerDirectory = new PeerDirectory();
    private final Map<String, P2PClient> connections = new HashMap<>();

    public P2PService(int port) {
        this.port = port;
    }

    @Override
    public void run() {

        // start server to accept inbound connections

        ExecutorService executor = Executors.newCachedThreadPool();
        P2PServer server = new P2PServer("localhost", port);
        var localAddr = server.getAddress();
        executor.submit(server);

        // establish outbound connections

        for (String peerAddr : peerDirectory.getKnownPeers()) {
            if (peerAddr.equals(localAddr))
                continue;

            try {
                var client = new P2PClient(peerAddr, localAddr);
                connections.put(peerAddr, client);
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
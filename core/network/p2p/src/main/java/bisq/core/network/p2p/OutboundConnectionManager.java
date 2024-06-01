package bisq.core.network.p2p;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;

import static bisq.core.network.p2p.P2PCategory.log;

public class OutboundConnectionManager implements Runnable {

    private final Map<String, P2PClient> connections = new HashMap<>();
    private final String myAddr;
    private final PeerDirectory peerDirectory;

    public OutboundConnectionManager(String myAddr, PeerDirectory peerDirectory) {
        this.myAddr = myAddr;
        this.peerDirectory = peerDirectory;
    }

    @Override
    public void run() {
        for (String peerAddr : peerDirectory.getKnownPeers()) {
            if (peerAddr.equals(myAddr))
                continue;

            try {
                var client = new P2PClient(peerAddr, myAddr);
                connections.put(peerAddr, client);
                //var ret = client.getPeers();
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
    }

    public void stop() {
        log.info("Closing outbound connections");
    }
}

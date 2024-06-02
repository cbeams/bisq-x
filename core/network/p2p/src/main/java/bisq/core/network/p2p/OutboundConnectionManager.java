package bisq.core.network.p2p;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;

import static bisq.core.network.p2p.P2PCategory.log;

class OutboundConnectionManager implements Runnable {

    private final PeerAddress selfAddress;
    private final PeerAddresses peerAddresses;
    private final HashMap<PeerAddress, OutboundConnection> connections = new HashMap<>();

    public OutboundConnectionManager(PeerAddress selfAddress, PeerAddresses peerAddresses) {
        this.selfAddress = selfAddress;
        this.peerAddresses = peerAddresses;
    }

    @Override
    public void run() {
        for (var peerAddress : peerAddresses.getAddresses()) {
            try {
                var conn = OutboundConnection.open(selfAddress, peerAddress);
                connections.put(peerAddress, conn);
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
    }

    public void stop() {
        log.info("Closing outbound connections");
        connections.values().forEach(OutboundConnection::close);
    }
}

package bisq.core.network.p2p;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;

import static bisq.core.network.p2p.P2PCategory.log;

class OutboundConnectionManager implements Runnable {

    private final Address self;
    private final AddressManager addressManager;
    private final HashMap<Address, OutboundConnection> connections = new HashMap<>();

    public OutboundConnectionManager(Address self, AddressManager addressManager) {
        this.self = self;
        this.addressManager = addressManager;
    }

    @Override
    public void run() {
        for (var peer : addressManager.getAddresses()) {
            try {
                var conn = OutboundConnection.open(self, peer);
                connections.put(peer, conn);
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

package bisq.core.network.p2p;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.HashSet;

import static bisq.core.network.p2p.P2PCategory.log;

class OutboundConnectionManager implements Runnable, AddressListener {

    private static final int TARGET_CONNECTION_COUNT = 8;

    private final Address self;
    private final AddressManager addressManager;
    private final HashMap<Address, OutboundConnection> connections = new HashMap<>();
    private final HashSet<OutboundConnectionListener> outboundConnectionListeners = new HashSet<>();


    public OutboundConnectionManager(Address self, AddressManager addressManager) {
        this.self = self;
        this.addressManager = addressManager;
    }

    public void addOutboundConnectionListener(OutboundConnectionListener listener) {
        outboundConnectionListeners.add(listener);
    }

    @Override
    public void run() {
        for (var addr : addressManager.getAddresses()) {
            try {
                openConnection(addr);
                if (connections.size() >= TARGET_CONNECTION_COUNT)
                    break;
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
        log.info("Opened a total of {} outbound connections", connections.size());
    }

    private void openConnection(Address addr) throws IOException {
        var conn = OutboundConnection.open(self, addr);
        connections.put(addr, conn);
        for (var listener : outboundConnectionListeners)
            listener.onNewOutboundConnection(conn);
    }

    @Override
    public void onAddAddress(Address addr) throws IOException {
        if (connections.size() < TARGET_CONNECTION_COUNT)
            connections.put(addr, OutboundConnection.open(self, addr));
    }

    public void stop() {
        log.info("Closing outbound connections");
        connections.values().forEach(OutboundConnection::close);
    }
}

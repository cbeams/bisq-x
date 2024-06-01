package bisq.core.network.p2p;

import static bisq.core.network.p2p.P2PCategory.log;

public class P2PService {

    private final InboundConnectionManager icm;
    private final OutboundConnectionManager ocm;

    public P2PService(int port) {
        var peerDirectory = new PeerDirectory();
        this.icm = new InboundConnectionManager("localhost", port, peerDirectory);
        this.ocm = new OutboundConnectionManager(icm.getAddress(), peerDirectory);
    }

    public void start() {
        log.info("Starting p2p service");
        new Thread(icm).start();
        new Thread(ocm).start();
    }

    public void stop() {
        log.info("Stopping p2p service");
        ocm.stop();
        icm.stop();
    }
}
package bisq.core.network.p2p;

import static bisq.core.network.p2p.P2PCategory.log;

public class PeerService {

    private final InboundConnectionManager icm;
    private final OutboundConnectionManager ocm;

    public PeerService(int port) {
        var selfAddress = new PeerAddress("localhost", port);
        var peerAddresses = PeerAddresses.excluding(selfAddress);
        icm = new InboundConnectionManager(selfAddress);
        ocm = new OutboundConnectionManager(selfAddress, peerAddresses);
        icm.addRequestHandler(peerAddresses);
    }

    public void start() {
        log.info("Starting peer service");
        new Thread(icm).start();
        new Thread(ocm).start();
    }

    public void stop() {
        log.info("Stopping peer service");
        ocm.stop();
        icm.stop();
    }
}
package bisq.core.network.p2p;

import static bisq.core.network.p2p.P2PCategory.log;

public class PeerService {

    private final InboundConnectionManager icm;
    private final OutboundConnectionManager ocm;

    public PeerService(int port) {
        var self = new Address("localhost", port);
        var addressManager = AddressManager.excluding(self);
        icm = new InboundConnectionManager(self);
        ocm = new OutboundConnectionManager(self, addressManager);

        icm.addRequestHandler(addressManager);
        icm.addInboundConnectionListener(addressManager);

        ocm.addOutboundConnectionListener(addressManager);

        addressManager.addAddressListener(ocm);
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
package bisq.core.network.p2p;

import bisq.core.network.p2p.P2PMessage.Operation;

public interface P2PMessageListener {

    void onMessage(P2PMessage message, Operation operation);
}

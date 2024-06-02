package bisq.core.network.p2p;

import java.io.IOException;

public interface OutboundConnectionListener {

    void onNewOutboundConnection(OutboundConnection outboundConnection) throws IOException;
}

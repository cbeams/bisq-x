package bisq.core.network.p2p;

import java.io.IOException;

public interface InboundConnectionListener {

    void onNewInboundConnection(InboundConnection inboundConnection) throws IOException;
}

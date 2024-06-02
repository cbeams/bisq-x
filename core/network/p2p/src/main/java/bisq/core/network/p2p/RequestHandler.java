package bisq.core.network.p2p;

import java.io.IOException;
import java.net.Socket;

public interface RequestHandler {
    boolean canHandle(String requestType);

    void handle(String requestType, Socket socket) throws IOException;
}

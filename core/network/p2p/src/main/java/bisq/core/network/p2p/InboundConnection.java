package bisq.core.network.p2p;

import bisq.core.network.p2p.proto.P2P;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.Socket;
import java.util.Set;

import static bisq.core.network.p2p.P2PCategory.log;

class InboundConnection implements Runnable, Closeable {

    private final PeerAddress fromAddr;
    private final Socket socket;
    private final Set<RequestHandler> requestHandlers;

    public InboundConnection(PeerAddress fromAddr, Socket socket, Set<RequestHandler> requestHandlers) {
        this.fromAddr = fromAddr;
        this.socket = socket;
        this.requestHandlers = requestHandlers;
    }

    @Override
    public void run() {
        try {
            var input = socket.getInputStream();

            while (true) {
                var requestType = P2P.RequestType.parseDelimitedFrom(input);
                if (requestType == null) {
                    break;
                }

                var type = requestType.getValue();
                boolean handled = false;
                for (RequestHandler requestHandler : requestHandlers) {
                    if (requestHandler.canHandle(type)) {
                        requestHandler.handle(type, socket);
                        handled = true;
                    }
                }
                if (!handled) {
                    log.error("Error: unsupported request type: {}", requestType);
                    this.close();
                    break;
                }
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public void close() {
        log.info("Closing inbound connection from {}", fromAddr);
        //try {
            //socket.close();
        //} catch (IOException ex) {
            //throw new UncheckedIOException(ex);
        //}
    }
}

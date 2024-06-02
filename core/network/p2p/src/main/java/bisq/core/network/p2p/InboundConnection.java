package bisq.core.network.p2p;

import bisq.core.network.p2p.proto.P2P;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.Socket;
import java.util.Set;

import static bisq.core.network.p2p.P2PCategory.log;

class InboundConnection implements Runnable, Closeable {

    private final Address peer;
    private final Socket socket;
    private final Set<RequestHandler> requestHandlers;

    public InboundConnection(Address peer, Socket socket, Set<RequestHandler> requestHandlers) {
        this.peer = peer;
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
                for (var handler : requestHandlers) {
                    if (handler.canHandle(type)) {
                        handler.handle(type, socket);
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
        log.info("Closing inbound connection from {}", peer);
        //try {
            //socket.close();
        //} catch (IOException ex) {
            //throw new UncheckedIOException(ex);
        //}
    }
}

package bisq.core.network.p2p;

import bisq.core.network.p2p.proto.P2P;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.HashSet;

import static bisq.core.network.p2p.P2PCategory.log;

class InboundConnectionManager implements Runnable {

    private final Address self;
    private final HashSet<RequestHandler> requestHandlers = new HashSet<>();
    private final HashMap<Address, InboundConnection> connections = new HashMap<>();
    private final HashSet<InboundConnectionListener> inboundConnectionListeners = new HashSet<>();

    public InboundConnectionManager(Address self) {
        this.self = self;
    }

    public void addRequestHandler(RequestHandler requestHandler) {
        requestHandlers.add(requestHandler);
    }

    public void addInboundConnectionListener(InboundConnectionListener listener) {
        inboundConnectionListeners.add(listener);
    }

    @Override
    public void run() {
        try (var serverSocket = new ServerSocket(self.port())) {
            log.info("Accepting inbound connections at {}", self);
            while (true) {
                var socket = serverSocket.accept();
                var input = socket.getInputStream();
                var requestType = P2P.RequestType.parseDelimitedFrom(input);

                if (!"connect".equalsIgnoreCase(requestType.getValue())) {
                    log.error("Error: expected 'connect' request but got {}", requestType);
                    socket.close();
                    break;
                }

                var peer = Address.fromString(P2P.ConnectionRequest.parseDelimitedFrom(input).getFromAddress());

                log.info("Accepted inbound connection from {}", peer);

                var conn = new InboundConnection(peer, socket, requestHandlers);
                connections.put(peer, conn);

                new Thread(conn).start();

                for (var listener : inboundConnectionListeners)
                    listener.onNewInboundConnection(conn);
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public void stop() {
        log.info("Closing inbound connections");
        connections.values().forEach(InboundConnection::close);
    }
}
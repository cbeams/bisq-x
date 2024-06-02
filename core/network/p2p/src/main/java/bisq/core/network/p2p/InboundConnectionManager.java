package bisq.core.network.p2p;

import bisq.core.network.p2p.proto.P2P;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.util.HashMap;

import static bisq.core.network.p2p.P2PCategory.log;

class InboundConnectionManager implements Runnable {

    private final PeerAddress selfAddress;
    private final PeerAddresses peerAddresses;
    private final HashMap<PeerAddress, InboundConnectionHandler> connections = new HashMap<>();

    public InboundConnectionManager(PeerAddress selfAddress, PeerAddresses peerAddresses) {
        this.selfAddress = selfAddress;
        this.peerAddresses = peerAddresses;
    }

    @Override
    public void run() {
        try (var serverSocket = new ServerSocket(selfAddress.port())) {
            log.info("Accepting inbound connections at {}", selfAddress);
            while (true) {
                var socket = serverSocket.accept();
                var input = socket.getInputStream();
                var requestType = P2P.RequestType.parseDelimitedFrom(input);

                if (!"connect".equalsIgnoreCase(requestType.getValue())) {
                    log.error("Error: expected 'connect' request but got {}", requestType);
                    socket.close();
                    break;
                }

                var peerAddr = PeerAddress.fromString(P2P.ConnectionRequest.parseDelimitedFrom(input).getFromAddress());

                log.info("Accepted inbound connection from {}", peerAddr);

                var conn = new InboundConnectionHandler(peerAddr, socket, peerAddresses);
                connections.put(peerAddr, conn);
                new Thread(conn).start();
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public void stop() {
        log.info("Closing inbound connections");
        connections.values().forEach(InboundConnectionHandler::close);
    }
}

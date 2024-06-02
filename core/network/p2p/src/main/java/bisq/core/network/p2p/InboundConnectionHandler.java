package bisq.core.network.p2p;

import bisq.core.network.p2p.proto.P2P;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.Socket;

import static bisq.core.network.p2p.P2PCategory.log;
import static java.util.stream.Collectors.toSet;

class InboundConnectionHandler implements Runnable {

    private final PeerAddress fromAddr;
    private final Socket socket;
    private final PeerAddresses peerAddresses;

    public InboundConnectionHandler(PeerAddress fromAddr, Socket socket, PeerAddresses peerAddresses) {
        this.fromAddr = fromAddr;
        this.socket = socket;
        this.peerAddresses = peerAddresses; // TODO: remove, extract to listener
    }

    @Override
    public void run() {
        try {
            var input = socket.getInputStream();
            var output = socket.getOutputStream();

            REQUEST_LOOP:
            while (true) {
                var requestType = P2P.RequestType.parseDelimitedFrom(input);
                if (requestType == null) {
                    break;
                }

                switch (requestType.getValue()) {
                    case "get_peers" -> {
                        var peerList = P2P.PeerList.newBuilder().addAllPeers(
                                        peerAddresses.getAddresses().stream()
                                                .map(peerAddr ->
                                                        P2P.Peer.newBuilder()
                                                                .setAddress(peerAddr.toString())
                                                                .build()
                                                ).collect(toSet()))
                                .build();
                        peerList.writeDelimitedTo(output);
                    }
                    default -> {
                        log.error("Error: unsupported request type: {}", requestType);
                        this.close();
                        break REQUEST_LOOP;
                    }
                }
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public void close() {
        log.info("Closing inbound connection from {}", fromAddr);
        try {
            socket.close();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}

package bisq.core.network.p2p;

import bisq.core.network.p2p.proto.P2P;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import static bisq.core.network.p2p.P2PCategory.log;
import static java.util.stream.Collectors.toSet;

class P2PServer implements Runnable {

    final String host;
    final int port;
    private final Set<String> peers = new HashSet<>();

    public P2PServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {

        try (var serverSocket = new ServerSocket(port)) {
            log.info("Listening for peer connections at bisq://{}:{}", host, port);
            while (true) {
                var conn = serverSocket.accept();
                var input = conn.getInputStream();
                var requestType = P2P.RequestType.parseDelimitedFrom(input);

                if (!"connect".equalsIgnoreCase(requestType.getValue())) {
                    log.error("Error: expected 'connect' request but got {}", requestType);
                    conn.close();
                    break;
                }

                var peerAddr = P2P.ConnectionRequest.parseDelimitedFrom(input).getAddress();

                if (!peers.add(peerAddr)) {
                    log.warn("Warning: already connected to {}", peerAddr);
                }

                new Thread(new ConnectionHandler(conn)).start();
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public String getAddress() {
        return String.format("%s:%d", host, port);
    }


    class ConnectionHandler implements Runnable {

        private final Socket conn;

        public ConnectionHandler(Socket conn) {
            this.conn = conn;
        }

        @Override
        public void run() {
            try {
                var input = conn.getInputStream();
                var output = conn.getOutputStream();

                REQUEST_LOOP:
                while (true) {
                    var requestType = P2P.RequestType.parseDelimitedFrom(input);
                    if (requestType == null) {
                        break;
                    }

                    switch (requestType.getValue()) {
                        case "get_peers" -> {
                            var peerList = P2P.PeerList.newBuilder().addAllPeers(
                                            peers.stream()
                                                    .map(peerAddr ->
                                                            P2P.Peer.newBuilder()
                                                                    .setAddress(peerAddr)
                                                                    .build()
                                                    ).collect(toSet()))
                                    .build();
                            peerList.writeDelimitedTo(output);
                        }
                        default -> {
                            log.error("Error: unsupported request type: {}", requestType);
                            conn.close();
                            break REQUEST_LOOP;
                        }
                    }
                }
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
    }
}

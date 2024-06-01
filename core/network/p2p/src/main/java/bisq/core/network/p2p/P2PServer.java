package bisq.core.network.p2p;

import bisq.core.network.p2p.proto.P2P;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

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
        System.out.println("P2PServer.run");

        try (var serverSocket = new ServerSocket(port)) {
            while (true) {
                var conn = serverSocket.accept();
                var input = conn.getInputStream();
                var requestType = P2P.RequestType.parseDelimitedFrom(input);

                if (!"connect".equalsIgnoreCase(requestType.getValue())) {
                    System.out.println("Error: expected 'connect' request but got " + requestType);
                    conn.close();
                    break;
                }

                var peerAddr = P2P.ConnectionRequest.parseDelimitedFrom(input).getAddress();

                System.out.println("peerAddr = " + peerAddr);

                if (!peers.add(peerAddr)) {
                    System.out.println("Error: already connected to " + peerAddr);
                    conn.close();
                    break;
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
                    System.out.println("got request type '" + requestType + "'");

                    switch (requestType.getValue()) {
                        case "get_peers" -> {
                            var req = P2P.GetPeersRequest.parseDelimitedFrom(input);
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
                            System.out.println("Error: unsupported request type: " + requestType);
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

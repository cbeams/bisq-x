package bisq.core.network.p2p;

import bisq.core.network.p2p.proto.P2P;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.Socket;

import static bisq.core.network.p2p.P2PCategory.log;

class OutboundConnection implements Closeable {

    private final Socket socket;
    private final PeerAddress toAddr;

    public static OutboundConnection open(PeerAddress fromAddr, PeerAddress toAddr) throws IOException {

        var socket = new Socket(toAddr.host(), toAddr.port());

        var output = socket.getOutputStream();

        var requestType = P2P.RequestType.newBuilder().setValue("connect").build();
        requestType.writeDelimitedTo(output);

        var connectRequest = P2P.ConnectionRequest.newBuilder().setFromAddress(fromAddr.toString()).build();
        connectRequest.writeDelimitedTo(output);

        log.info("Opened outbound connection to {}", toAddr);

        return new OutboundConnection(toAddr, socket);
    }

    private OutboundConnection(PeerAddress toAddr, Socket socket) {
        this.toAddr = toAddr;
        this.socket = socket;
    }

    /*
    public Set<String> getPeers() throws IOException {
        P2P.RequestType.newBuilder().setValue("get_peers").build().writeDelimitedTo(output);
        var peerList = P2P.PeerList.parseDelimitedFrom(input);
        return peerList.getPeersList().stream().map(P2P.Peer::getAddress).collect(Collectors.toSet());
    }
     */

    @Override
    public void close() {
        try {
            log.info("Closing outbound connection to {}", toAddr);
            socket.close();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}

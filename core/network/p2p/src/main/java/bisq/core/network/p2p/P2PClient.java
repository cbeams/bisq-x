package bisq.core.network.p2p;

import bisq.core.network.p2p.proto.P2P;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Set;
import java.util.stream.Collectors;

class P2PClient {

    private final InputStream input;
    private final OutputStream output;

    public P2PClient(String destAddr, String originAddr) throws IOException {
        var addr = destAddr.split(":");
        var host = addr[0];
        var port = Integer.parseInt(addr[1]);
        var socket = new Socket(host, port);

        this.input = socket.getInputStream();
        this.output = socket.getOutputStream();

        var requestType = P2P.RequestType.newBuilder().setValue("connect").build();
        requestType.writeDelimitedTo(output);

        var connectRequest = P2P.ConnectionRequest.newBuilder().setAddress(originAddr).build();
        connectRequest.writeDelimitedTo(output);

        P2PCategory.log.info("Established outbound connection to bisq://{}", destAddr);
    }

    public Set<String> getPeers() throws IOException {
        P2P.RequestType.newBuilder().setValue("get_peers").build().writeDelimitedTo(output);
        var peerList = P2P.PeerList.parseDelimitedFrom(input);
        return peerList.getPeersList().stream().map(P2P.Peer::getAddress).collect(Collectors.toSet());
    }
}

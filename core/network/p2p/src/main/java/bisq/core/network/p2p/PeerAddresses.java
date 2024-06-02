package bisq.core.network.p2p;

import bisq.core.network.p2p.proto.P2P;

import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;

public class PeerAddresses implements RequestHandler {

    private static final PeerAddress SEED_NODE_1 = new PeerAddress("localhost", 2140);
    private final Set<PeerAddress> addresses = new HashSet<>();
    private final Predicate<PeerAddress> addressFilter;

    private PeerAddresses(Predicate<PeerAddress> addressFilter) {
        this.addresses.add(SEED_NODE_1);
        this.addressFilter = addressFilter;
    }

    public static PeerAddresses excluding(PeerAddress excludedAddress) {
        return new PeerAddresses(address -> !address.equals(excludedAddress));
    }

    public Set<PeerAddress> getAddresses() {
        return addresses.stream().filter(addressFilter).collect(toSet());
    }

    @Override
    public boolean canHandle(String requestType) {
        return "get_peers".equals(requestType);
    }

    @Override
    public void handle(String requestType, Socket socket) throws IOException {
        if (!canHandle(requestType))
            throw new IllegalArgumentException(
                    format("unsupported request type '%s'", requestType));

        var peerList = P2P.PeerList.newBuilder()
                .addAllPeers(
                        getAddresses()
                                .stream()
                                .map(peerAddr ->
                                        P2P.Peer.newBuilder()
                                                .setAddress(peerAddr.toString())
                                                .build())
                                .collect(toSet()))
                .build();

        peerList.writeDelimitedTo(socket.getOutputStream());
    }
}
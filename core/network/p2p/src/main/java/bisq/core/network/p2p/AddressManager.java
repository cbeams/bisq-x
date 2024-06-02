package bisq.core.network.p2p;

import bisq.core.network.p2p.proto.P2P;

import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;

public class AddressManager implements RequestHandler, InboundConnectionListener, OutboundConnectionListener {

    private static final String GET_PEERS_REQUEST_TYPE = "get_peers";

    private static final Address SEED_NODE_1 = new Address("localhost", 2140);

    private final Predicate<Address> addressFilter;
    private final Set<Address> addresses = new HashSet<>();
    private final Set<AddressListener> addressListeners = new HashSet<>();

    private AddressManager(Predicate<Address> addressFilter) {
        this.addresses.add(SEED_NODE_1);
        this.addressFilter = addressFilter;
    }

    public static AddressManager excluding(Address excludedAddress) {
        return new AddressManager(address -> !address.equals(excludedAddress));
    }

    public void addAddressListener(AddressListener addressListener) {
        addressListeners.add(addressListener);
    }

    public Set<Address> getAddresses() {
        return addresses.stream().filter(addressFilter).collect(toSet());
    }

    @Override
    public boolean canHandle(String requestType) {
        return GET_PEERS_REQUEST_TYPE.equals(requestType);
    }

    @Override
    public void handle(String requestType, Socket socket) throws IOException {
        if (GET_PEERS_REQUEST_TYPE.equals(requestType))
            handleGetPeersRequest(socket);
        else
            throw new IllegalArgumentException(
                    format("unsupported request type '%s'", requestType));
    }

    private void handleGetPeersRequest(Socket socket) throws IOException {
        P2P.PeerList.newBuilder()
                .addAllPeers(
                        this.getAddresses()
                                .stream()
                                .map(peerAddr ->
                                        P2P.Peer.newBuilder()
                                                .setAddress(peerAddr.toString())
                                                .build())
                                .collect(toSet()))
                .build()
                .writeDelimitedTo(socket.getOutputStream());
    }

    @Override
    public void onNewInboundConnection(InboundConnection inboundConnection) throws IOException {
        addAddress(inboundConnection.getAddress());
    }

    @Override
    public void onNewOutboundConnection(OutboundConnection outboundConnection) throws IOException {
        sendGetPeersRequest(outboundConnection);
    }

    private void sendGetPeersRequest(OutboundConnection outboundConnection) throws IOException {
        var req = P2P.RequestType.newBuilder().setValue(GET_PEERS_REQUEST_TYPE).build();
        req.writeDelimitedTo(outboundConnection.getSocket().getOutputStream());

        var res = P2P.PeerList.parseDelimitedFrom(outboundConnection.getSocket().getInputStream());
        for (P2P.Peer peer : res.getPeersList()) {
            addAddress(Address.fromString(peer.getAddress()));
        }
    }

    public void addAddress(Address address) throws IOException {
        if (addressFilter.test(address) && addresses.add(address))
            for (var listener : addressListeners)
                listener.onAddAddress(address);
    }
}
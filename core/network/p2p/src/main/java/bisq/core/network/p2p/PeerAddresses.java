package bisq.core.network.p2p;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toSet;

public class PeerAddresses {

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
}

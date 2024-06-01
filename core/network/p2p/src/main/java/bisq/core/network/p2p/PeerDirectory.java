package bisq.core.network.p2p;

import java.util.HashSet;
import java.util.Set;

public class PeerDirectory {

    public static final String SEED_NODE_1 = "localhost:2140";

    private final Set<String> knownPeers = new HashSet<>();

    public PeerDirectory() {
        knownPeers.add(SEED_NODE_1);
    }

    public Set<String> getKnownPeers() {
        return knownPeers;
    }
}

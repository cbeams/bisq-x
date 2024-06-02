package bisq.core.network.p2p;

import java.io.IOException;

public interface AddressListener {

    void onAddAddress(Address address) throws IOException;
}

package bisq.core.network.p2p;

import bisq.core.util.logging.Logging;
import org.slf4j.Logger;

public interface P2PLog {

    String P2P_LOG_NAME = "p2p";

    Logger log = Logging.getLog(P2P_LOG_NAME);
}

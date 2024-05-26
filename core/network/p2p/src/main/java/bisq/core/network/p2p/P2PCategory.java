package bisq.core.network.p2p;

import bisq.core.logging.Category;
import bisq.core.logging.Logging;
import ch.qos.logback.classic.Logger;

public interface P2PCategory extends Category {

    Logger log = Logging.createCategoryLogger("p2p");
}

package bisq.core.node.app;

import bisq.core.logging.Category;
import bisq.core.logging.Logging;
import ch.qos.logback.classic.Logger;

public interface AppCategory extends Category {

    Logger log = Logging.createCategoryLogger("app");
}

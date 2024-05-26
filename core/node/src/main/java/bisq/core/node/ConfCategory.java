package bisq.core.node;

import bisq.core.logging.Category;
import bisq.core.logging.Logging;
import ch.qos.logback.classic.Logger;

public interface ConfCategory extends Category {

    Logger log = Logging.createCategoryLogger("conf");
}

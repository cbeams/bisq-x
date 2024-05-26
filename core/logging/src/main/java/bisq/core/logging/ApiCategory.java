package bisq.core.logging;

import ch.qos.logback.classic.Logger;

public interface ApiCategory extends Category {

    Logger log = Logging.createCategoryLogger("api");
}

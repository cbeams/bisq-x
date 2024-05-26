package bisq.core.logging;

import ch.qos.logback.classic.Logger;

public interface LogCategory extends Category {

    Logger log = Logging.createCategoryLogger("log");
}

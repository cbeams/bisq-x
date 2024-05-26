package bisq.core.network.http;

import bisq.core.logging.Category;
import bisq.core.logging.Logging;
import ch.qos.logback.classic.Logger;

interface HttpCategory extends Category {

    Logger log = Logging.createCategoryLogger("http");
}

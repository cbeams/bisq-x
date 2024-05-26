package bisq.core.domain.trade;

import bisq.core.logging.Category;
import bisq.core.logging.Logging;
import ch.qos.logback.classic.Logger;

public interface OfferCategory extends Category {

    Logger log = Logging.createCategoryLogger("offer");
}

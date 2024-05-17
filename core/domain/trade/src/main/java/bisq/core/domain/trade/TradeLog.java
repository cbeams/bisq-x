package bisq.core.domain.trade;

import bisq.core.logging.Logging;
import org.slf4j.Logger;

public interface TradeLog {

    String TRADE_LOG_NAME = "trade";

    Logger log = Logging.getLog(TRADE_LOG_NAME);
}

package bisq.core.node;

import bisq.core.util.logging.Logging;
import org.slf4j.Logger;

public interface OptionsLog {

    String OPTIONS_LOG_NAME = "opts";

    Logger log = Logging.getLog(OPTIONS_LOG_NAME);
}

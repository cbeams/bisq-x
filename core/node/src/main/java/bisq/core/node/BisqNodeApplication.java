package bisq.core.node;

import bisq.core.util.logging.Logging;
import org.slf4j.Logger;

/**
 * Indicates that an implementing class configures and runs a {@link BisqNode}, typically
 * from a main method entry point.
 */
public interface BisqNodeApplication {

    int EXIT_SUCCESS = 0;
    int EXIT_FAILURE = 1;

    String APP_LOG_NAME = "app";

    Logger log = Logging.getLog(APP_LOG_NAME);
}

package bisq.core.node;

import bisq.core.logging.Logging;
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

    /**
     * Unwraps excessive exception nesting for better log output
     */
    static Throwable unwrap(Throwable t) {
        while (t.getCause() != null && t.getMessage().contains("Exception")) {
            t = t.getCause();
        }
        return t;
    }
}

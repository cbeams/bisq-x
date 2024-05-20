package bisq.core.node.app;

import bisq.core.logging.Logging;
import bisq.core.node.BisqNode;
import org.slf4j.Logger;

/**
 * Indicates that an implementing class configures and runs a {@link BisqNode}, typically
 * from a main method entry point.
 */
public interface BisqNodeApp {

    int EXIT_SUCCESS = 0;
    int EXIT_FAILURE = 1;

    Logger log = Logging.getLog("app");

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

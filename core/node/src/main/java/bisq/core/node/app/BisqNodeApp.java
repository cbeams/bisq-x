package bisq.core.node.app;

import bisq.core.node.BisqNode;
import ch.qos.logback.classic.Logger;

/**
 * Indicates that an implementing class configures and runs a {@link BisqNode}, typically
 * from a main method entry point.
 */
public interface BisqNodeApp {

    int EXIT_SUCCESS = 0;
    int EXIT_FAILURE = 1;

    Logger log = AppCategory.log;

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

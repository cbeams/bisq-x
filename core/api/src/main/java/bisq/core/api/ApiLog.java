package bisq.core.api;

import org.slf4j.Logger;

public abstract class ApiLog {

    public static String API_LOG_NAME = "api";

    // Gets set at static initialization time by core.logging.Logging class, which
    // the core.api package is too low-level to depend on at compile time.
    public static Logger log;
}

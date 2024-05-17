package bisq.core.util.logging;

import bisq.core.api.ApiLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.Collection;
import java.util.HashMap;

public class Logging {

    private static final HashMap<String, Logger> ALL_LOGS = new HashMap<>();

    private static Level EXPLICIT_DEFAULT_LEVEL;

    static {
        // special case creation and assignment of low-level api log
        ApiLog.log = getLog(ApiLog.API_LOG_NAME);
    }

    public static Logger getLog(String name) {
        if (ALL_LOGS.containsKey(name))
            return ALL_LOGS.get(name);

        var log = LoggerFactory.getLogger(name);

        if (EXPLICIT_DEFAULT_LEVEL != null)
            ((ch.qos.logback.classic.Logger) log)
                    .setLevel(ch.qos.logback.classic.Level.convertAnSLF4JLevel(EXPLICIT_DEFAULT_LEVEL));

        ALL_LOGS.put(name, log);
        return log;
    }

    public static void setLevel(Level level, Logger... logs) {
        if (logs.length == 0) {
            logs = ALL_LOGS.values().toArray(new Logger[0]);
            EXPLICIT_DEFAULT_LEVEL = level;
        }

        for (Logger log : logs)
            ((ch.qos.logback.classic.Logger) log)
                    .setLevel(ch.qos.logback.classic.Level.valueOf(level.name()));
    }

    public static Collection<Logger> getLogs() {
        return ALL_LOGS.values();
    }
}

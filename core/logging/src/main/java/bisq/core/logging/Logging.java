package bisq.core.logging;

import bisq.core.api.ApiLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import ch.qos.logback.classic.LoggerContext;

import java.util.Collection;
import java.util.HashMap;

public class Logging {

    private static final HashMap<String, Logger> allLogs = new HashMap<>();
    private static Level defaultLevel = getRootLevel();

    static {
        // special case creation and assignment of low-level api log
        ApiLog.log = getLog(ApiLog.API_LOG_NAME);
    }

    public static Logger getLog(String name) {
        if (allLogs.containsKey(name))
            return allLogs.get(name);

        var log = LoggerFactory.getLogger(name);

        ((ch.qos.logback.classic.Logger) log)
                .setLevel(ch.qos.logback.classic.Level.convertAnSLF4JLevel(defaultLevel));

        allLogs.put(name, log);

        return log;
    }

    public static void setLevel(Level level, Logger... logs) {
        if (logs.length == 0) {
            logs = allLogs.values().toArray(new Logger[0]);
            defaultLevel = level;
        }

        for (Logger log : logs)
            ((ch.qos.logback.classic.Logger) log)
                    .setLevel(ch.qos.logback.classic.Level.valueOf(level.name()));
    }

    public static Collection<Logger> getLogs() {
        return allLogs.values();
    }

    public static void enableLevel(Level level) {
        if (defaultLevel.compareTo(level) < 0)
            setLevel(level);
    }

    private static Level getRootLevel() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Level rootLevel = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).getLevel();
        return Level.valueOf(rootLevel.toString());
    }
}

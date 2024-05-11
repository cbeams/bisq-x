package bisq.core.util.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.Collection;
import java.util.HashMap;

public class Logging {

    public static final String CONF_LOG_NAME = "conf";
    public static final String HTTP_LOG_NAME = "http";
    public static final String NODE_LOG_NAME = "node";
    public static final String P2P_LOG_NAME = "p2p";
    public static final String TRADE_LOG_NAME = "trade";

    private static final HashMap<String, Logger> ALL_LOGS = new HashMap<>() {{
        put(CONF_LOG_NAME, LoggerFactory.getLogger(CONF_LOG_NAME));
        put(HTTP_LOG_NAME, LoggerFactory.getLogger(HTTP_LOG_NAME));
        put(NODE_LOG_NAME, LoggerFactory.getLogger(NODE_LOG_NAME));
        put(P2P_LOG_NAME, LoggerFactory.getLogger(P2P_LOG_NAME));
        put(TRADE_LOG_NAME, LoggerFactory.getLogger(TRADE_LOG_NAME));
    }};

    public static final Logger confLog = ALL_LOGS.get(CONF_LOG_NAME);
    public static final Logger httpLog = ALL_LOGS.get(HTTP_LOG_NAME);
    public static final Logger nodeLog = ALL_LOGS.get(NODE_LOG_NAME);
    public static final Logger p2pLog = ALL_LOGS.get(P2P_LOG_NAME);
    public static final Logger tradeLog = ALL_LOGS.get(TRADE_LOG_NAME);

    public static Logger getLog(String name) {
        if (ALL_LOGS.containsKey(name))
            return ALL_LOGS.get(name);

        var log = LoggerFactory.getLogger(name);
        ALL_LOGS.put(name, log);
        return log;
    }

    public static void setLevel(Level level, Logger... logs) {
        if (logs.length == 0)
            logs = ALL_LOGS.values().toArray(new Logger[0]);

        for (Logger log : logs)
            ((ch.qos.logback.classic.Logger) log)
                    .setLevel(ch.qos.logback.classic.Level.valueOf(level.name()));
    }

    public static Collection<Logger> getLogs() {
        return ALL_LOGS.values();
    }
}

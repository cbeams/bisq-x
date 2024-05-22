package bisq.core.logging;

import bisq.core.api.ApiLog;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class Logging {

    private static final HashMap<String, Logger> allLogs = new HashMap<>();
    private static final Logger log;
    private static Level defaultLevel;

    static {
        // Get the LoggerContext
        var context = (LoggerContext) LoggerFactory.getILoggerFactory();

        // Reset the context to clear any existing configuration
        context.reset();

        // Configure the encoder
        var encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d{yyyy-MM-dd'T'HH:mm:ss,UTC}Z [%4.-4logger] %msg%n");
        encoder.start();

        // Configure the ConsoleAppender
        var consoleAppender = new ConsoleAppender<ILoggingEvent>();
        consoleAppender.setContext(context);
        consoleAppender.setEncoder(encoder);
        consoleAppender.start();

        // Configure the root logger
        ch.qos.logback.classic.Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        ch.qos.logback.classic.Level rootLevel = ch.qos.logback.classic.Level.INFO;
        rootLogger.setLevel(rootLevel);

        // Set the default level used for all Bisq logs to the same level.
        defaultLevel = Level.valueOf(rootLevel.toString());

        // Add the ConsoleAppender
        rootLogger.addAppender(consoleAppender);

        // Suppress all non-Bisq loggers to ERROR by default
        // Micronaut is the only one requiring this so far
        ch.qos.logback.classic.Logger micronautLogger = context.getLogger("io.micronaut");
        micronautLogger.setLevel(ch.qos.logback.classic.Level.ERROR);


        // Yes: 'log' is the log used to log about logging
        log = getLog("log");

        // Create and assign a log for the api module which is too low-level
        // to have its own compile-time time dependency on the logging module
        ApiLog.log = getLog(ApiLog.API_LOG_NAME);
    }

    public static Collection<Logger> getLogs() {
        return allLogs.values();
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

        log.debug("Setting {}log level to {}",
                logs.length == allLogs.size() ? "" : Arrays.stream(logs).map(Logger::getName).toList() + " ",
                level.toString().toLowerCase()
        );
    }

    public static void enableLevel(Level level) {
        if (defaultLevel.compareTo(level) < 0)
            setLevel(level);
    }
}
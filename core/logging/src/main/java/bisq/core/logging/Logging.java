package bisq.core.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;

import java.io.File;
import java.util.List;

import static bisq.core.logging.LogCategory.log;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class Logging {

    private static final String BASE_CATEGORY_LOGGER_NAME = "bisq";
    private static final String CATEGORY_LOGGER_PREFIX = format("%s.", BASE_CATEGORY_LOGGER_NAME);

    private static final LoggerContext loggerContext = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
    private static final PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
    private static final Logger baseCategoryLogger = loggerContext.getLogger(BASE_CATEGORY_LOGGER_NAME);

    static {
        // Reset the context to clear default configuration
        loggerContext.reset();

        PatternLayout.DEFAULT_CONVERTER_MAP.put("categoryDisplayName", CategoryDisplayNameConverter.class.getName());
        patternLayoutEncoder.setContext(loggerContext);
        patternLayoutEncoder.setPattern("%d{yyyy-MM-dd'T'HH:mm:ss,UTC}Z [%5.-5categoryDisplayName] %msg%n");
        patternLayoutEncoder.start();

        var consoleAppender = new ConsoleAppender<ILoggingEvent>();
        consoleAppender.setContext(loggerContext);
        consoleAppender.setEncoder(patternLayoutEncoder);
        consoleAppender.setTarget("System.out");
        consoleAppender.start();

        // Turn the root logger off entirely and do not attach any appender to it;
        // this ensures there is no spurious logging from libraries and frameworks
        loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).setLevel(Level.OFF);

        // Set default log level and begin console logging
        baseCategoryLogger.setLevel(Level.INFO);
        baseCategoryLogger.addAppender(consoleAppender);
    }

    public static Level getLevel() {
        var baseLevel = baseCategoryLogger.getLevel();
        if (baseLevel == null)
            throw new IllegalStateException("The level of the base category logger must not be null");

        return baseLevel;
    }

    public static void setLevel(Level level) {
        if (getLevel().levelInt == level.levelInt)
            return;

        baseCategoryLogger.setLevel(level);
        log.debug("Setting log level to {}", level.levelStr.toLowerCase());
    }

    public static void addFileAppender(File file) {
        var fileAppender = new FileAppender<ILoggingEvent>();
        fileAppender.setContext(loggerContext);
        fileAppender.setEncoder(patternLayoutEncoder);
        fileAppender.setFile(file.getAbsolutePath());
        fileAppender.start();

        baseCategoryLogger.addAppender(fileAppender);
    }

    public static CategorySpec getCategorySpec(String name) {
        return getCategorySpec(getCategoryLogger(name));
    }

    public static CategorySpec getCategorySpec(Logger logger) {
        return new CategorySpec(categoryDisplayNameFor(logger.getName()), logger.getEffectiveLevel().levelStr);
    }

    public static List<CategorySpec> getCategorySpecs() {
        return getCategoryLoggers().stream().map(Logging::getCategorySpec).collect(toList());
    }

    public static void update(CategorySpec categorySpec) {
        var name = categorySpec.name();
        // TODO: handle not found
        var logger = Logging.getCategoryLogger(name);
        String curLevel = logger.getEffectiveLevel().levelStr;
        String newLevel = categorySpec.level();
        if (!curLevel.equals(newLevel)) {
            log.info("Changing [{}] log level from {} to {}", name, curLevel, newLevel);
            logger.setLevel(Level.valueOf(newLevel));
        }
        // TODO: handle no change
    }

    public static Logger createCategoryLogger(String categoryDisplayName) {
        if (loggerContext.exists(categoryLoggerNameFor(categoryDisplayName)) != null) {
            // Print to stderr as well as throwing because the exception below gets thrown
            // during class initialization ends up buried in a NoClassDefFound exception
            var msg = format("a logger for category '%s' already exists", categoryDisplayName);
            System.err.println("Error: " + msg);
            throw new IllegalStateException(msg);
        }
        // create and return the new logger
        return loggerContext.getLogger(categoryLoggerNameFor(categoryDisplayName));
    }

    public static Logger getCategoryLogger(String categoryDisplayName) {
        var existingLogger = loggerContext.exists(categoryLoggerNameFor(categoryDisplayName));
        if (existingLogger == null) {
            // Print to stderr as well as throwing because the exception below gets thrown
            // during class initialization ends up buried in a NoClassDefFound exception
            var msg = format("no logger for category '%s' found; create it first", categoryDisplayName);
            System.err.println("Error: " + msg);
            throw new IllegalStateException(msg);
        }
        return existingLogger;
    }

    public static List<Logger> getCategoryLoggers() {
        return loggerContext.getLoggerList().stream().filter(Logging::isCategoryLogger).toList();
    }

    static String categoryLoggerNameFor(String categoryDisplayName) {
        if (isCategoryLoggerName(categoryDisplayName))
            throw new IllegalArgumentException(
                    format("'%s' is already a category logger name", categoryDisplayName));
        return CATEGORY_LOGGER_PREFIX + categoryDisplayName;
    }

    private static boolean isCategoryLogger(Logger logger) {
        return isCategoryLoggerName(logger.getName());
    }

    private static boolean isCategoryLoggerName(String candidateLoggerName) {
        return candidateLoggerName.startsWith(CATEGORY_LOGGER_PREFIX);
    }

    private static String categoryDisplayNameFor(String categoryLoggerName) {
        return categoryLoggerName.substring(CATEGORY_LOGGER_PREFIX.length());
    }

    public static class CategoryDisplayNameConverter extends ClassicConverter {
        @Override
        public String convert(ILoggingEvent event) {
            var loggerName = event.getLoggerName();

            if (isCategoryLoggerName(loggerName))
                return categoryDisplayNameFor(loggerName);

            return loggerName;
        }
    }
}
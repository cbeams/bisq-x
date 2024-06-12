/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

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
import ch.qos.logback.core.read.CyclicBufferAppender;

import java.io.File;
import java.util.List;

import static bisq.core.logging.LoggingSubsystem.log;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class LoggingService {

    private static final String BASE_CATEGORY_LOGGER_NAME = "bisq";
    private static final String CATEGORY_LOGGER_PREFIX = format("%s.", BASE_CATEGORY_LOGGER_NAME);

    private static final LoggerContext loggerContext = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
    private static final PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
    private static final Logger baseCategoryLogger = loggerContext.getLogger(BASE_CATEGORY_LOGGER_NAME);

    private static final ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
    private static final CyclicBufferAppender<ILoggingEvent> logFileBufferAppender = new CyclicBufferAppender<>();
    private static final FileAppender<ILoggingEvent> logFileAppender = new FileAppender<>();

    static {
        // Reset the context to clear default configuration
        loggerContext.reset();

        // Turn the root logger off entirely and do not attach any appender to it;
        // this prevents spurious logging from libraries and frameworks
        loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).setLevel(Level.OFF);

        // Configure log entry formatting
        PatternLayout.DEFAULT_CONVERTER_MAP.put("categoryName", CategoryNameConverter.class.getName());
        patternLayoutEncoder.setContext(loggerContext);
        patternLayoutEncoder.setPattern("%d{yyyy-MM-dd'T'HH:mm:ss,UTC}Z [%5.-5categoryName] %msg%n");
        patternLayoutEncoder.start();

        // Configure console appender
        consoleAppender.setContext(loggerContext);
        consoleAppender.setEncoder(patternLayoutEncoder);
        consoleAppender.setTarget("System.out");
        consoleAppender.start();

        // Configure buffering appender to be flushed to log file when located
        logFileBufferAppender.setContext(loggerContext);
        logFileBufferAppender.start();

        // Set default log level
        baseCategoryLogger.setLevel(Level.INFO);

        // Begin logging
        baseCategoryLogger.addAppender(consoleAppender);
        baseCategoryLogger.addAppender(logFileBufferAppender);
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

    public static void addLogFileAppender(File logFile) {
        if (logFileAppender.isStarted())
            throw new IllegalStateException("log file appender is already started");

        logFileAppender.setContext(loggerContext);
        logFileAppender.setEncoder(patternLayoutEncoder);
        logFileAppender.setFile(logFile.getAbsolutePath());
        logFileAppender.start();

        // Flush the buffer to the file appender
        for (int i = 0; i < logFileBufferAppender.getLength(); i++)
            logFileAppender.doAppend(logFileBufferAppender.get(i));

        baseCategoryLogger.detachAppender(logFileBufferAppender);
        baseCategoryLogger.addAppender(logFileAppender);

    }

    public static LoggingCategory getLoggingCategory(String name) {
        return getLoggingCategory(getCategoryLogger(name));
    }

    public static LoggingCategory getLoggingCategory(Logger logger) {
        return new LoggingCategory(categoryNameFor(logger.getName()), logger.getEffectiveLevel().levelStr);
    }

    public static List<LoggingCategory> getLoggingCategories() {
        return getCategoryLoggers().stream().map(LoggingService::getLoggingCategory).collect(toList());
    }

    public static void updateLoggingCategory(LoggingCategory loggingCategory) {
        var name = loggingCategory.name();
        // TODO: handle not found
        var logger = LoggingService.getCategoryLogger(name);
        String curLevel = logger.getEffectiveLevel().levelStr;
        String newLevel = loggingCategory.level();
        if (!curLevel.equals(newLevel)) {
            log.info("Changing [{}] log level from {} to {}", name, curLevel, newLevel);
            logger.setLevel(Level.valueOf(newLevel));
        }
        // TODO: handle no change
    }

    public static Logger createCategoryLogger(String categoryName) {
        if (loggerContext.exists(fqLoggerNameFor(categoryName)) != null) {
            // Print to stderr as well as throwing because the exception below gets thrown
            // during class initialization ends up buried in a NoClassDefFound exception
            var msg = format("a logger for category '%s' already exists", categoryName);
            System.err.println("Error: " + msg);
            throw new IllegalStateException(msg);
        }
        // create and return the new logger
        return loggerContext.getLogger(fqLoggerNameFor(categoryName));
    }

    public static Logger getCategoryLogger(String categoryName) {
        var existingLogger = loggerContext.exists(fqLoggerNameFor(categoryName));
        if (existingLogger == null) {
            // Print to stderr as well as throwing because the exception below gets thrown
            // during class initialization ends up buried in a NoClassDefFound exception
            var msg = format("no logger for category '%s' found; create it first", categoryName);
            System.err.println("Error: " + msg);
            throw new IllegalStateException(msg);
        }
        return existingLogger;
    }

    public static List<Logger> getCategoryLoggers() {
        return loggerContext.getLoggerList().stream().filter(LoggingService::isCategoryLogger).toList();
    }

    static String fqLoggerNameFor(String categoryName) {
        if (isCategoryLoggerName(categoryName))
            throw new IllegalArgumentException(
                    format("'%s' is already a category logger name", categoryName));
        return CATEGORY_LOGGER_PREFIX + categoryName;
    }

    private static boolean isCategoryLogger(Logger logger) {
        return isCategoryLoggerName(logger.getName());
    }

    private static boolean isCategoryLoggerName(String candidateLoggerName) {
        return candidateLoggerName.startsWith(CATEGORY_LOGGER_PREFIX);
    }

    private static String categoryNameFor(String categoryLoggerName) {
        return categoryLoggerName.substring(CATEGORY_LOGGER_PREFIX.length());
    }

    public static class CategoryNameConverter extends ClassicConverter {
        @Override
        public String convert(ILoggingEvent event) {
            var fqLoggerName = event.getLoggerName();

            if (isCategoryLoggerName(fqLoggerName))
                return categoryNameFor(fqLoggerName);

            return fqLoggerName;
        }
    }
}
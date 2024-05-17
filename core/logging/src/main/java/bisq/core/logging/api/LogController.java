package bisq.core.logging.api;

import bisq.core.api.ApiController;
import bisq.core.logging.Logging;
import ch.qos.logback.classic.Level;
import org.slf4j.Logger;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;

import io.swagger.v3.oas.annotations.Parameter;

import java.util.Collection;
import java.util.stream.Collectors;

@Controller("/logs")
public class LogController implements ApiController {

    private static final String LOGS_LOG_NAME = "logs";
    private static final Logger log = Logging.getLog(LOGS_LOG_NAME);

    @Get
    public Collection<LogConfig> get() {
        return Logging.getLogs().stream().map(logger ->
                new LogConfig(logger.getName(),
                        ((ch.qos.logback.classic.Logger) logger).getLevel().levelStr)).collect(Collectors.toList());
    }

    @Get("/{name}")
    public LogConfig get(String name) {
        ch.qos.logback.classic.Logger log = ((ch.qos.logback.classic.Logger) Logging.getLog(name));
        return new LogConfig(name, log.getLevel().levelStr);
    }

    @Put("/{name}")
    public void put(@Parameter String name, LogConfig logConfig) {
        ch.qos.logback.classic.Logger log = ((ch.qos.logback.classic.Logger) Logging.getLog(logConfig.name()));
        String curLevel = log.getLevel().toString();
        String newLevel = logConfig.level();
        if (!curLevel.equals(newLevel)) {
            LogController.log.info("Changing [{}] log level from {} to {}", name, curLevel, newLevel);
            log.setLevel(Level.valueOf(newLevel));
        }
    }
}


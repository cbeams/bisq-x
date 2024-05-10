package bisq.core.network.http;

import bisq.core.util.logging.Logging;
import org.slf4j.Logger;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.http.annotation.ResponseFilter;
import io.micronaut.http.annotation.ServerFilter;

@ServerFilter("/**")
class RestApiCallTraceFilter {

    private static final Logger log = Logging.apiLog;

    @RequestFilter
    public void logRequest(HttpRequest<?> request) {
        log.debug(request.toString());
    }

    @ResponseFilter
    public void logResponse(HttpResponse<?> response) {
        log.debug(response.toString());
    }
}

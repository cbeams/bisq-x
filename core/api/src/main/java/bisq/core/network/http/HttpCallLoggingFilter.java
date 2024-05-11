package bisq.core.network.http;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.http.annotation.ResponseFilter;
import io.micronaut.http.annotation.ServerFilter;

import static bisq.core.network.http.HttpLog.log;

@ServerFilter("/**")
class HttpCallLoggingFilter {

    @RequestFilter
    public void logRequest(HttpRequest<?> request) {
        log.debug(request.toString());
    }

    @ResponseFilter
    public void logResponse(HttpResponse<?> response) {
        log.debug(response.toString());
    }
}

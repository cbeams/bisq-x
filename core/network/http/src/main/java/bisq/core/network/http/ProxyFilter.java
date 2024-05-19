package bisq.core.network.http;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.filter.FilterChain;
import io.micronaut.http.filter.HttpFilter;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import reactor.core.publisher.Mono;

import jakarta.inject.Inject;

import static io.micronaut.http.HttpHeaders.FORWARDED;
import static io.micronaut.http.HttpHeaders.HOST;

/**
 * An experiment in implementing clearnet-to-Tor proxy support for e.g. Bisq Mobile clients.
 * It works, but is naive in many ways, and is probably not the approach to take in any
 * case. To use it, a client makes api requests just as they would have if communicating
 * with this node directly, but requests forwarding their request to a different
 * destination node—presumably their own or a friend's—by providing the X-Forward-To:
 * header with the hostname and port of the destination node as the value. The
 * implementation below creates a new request, copies the headers and body from the
 * original and forwards it on to the destination node as requested, but first strips out
 * the X-Forward-To header and uses its value as the value of the new request's Host
 * header. The response returned from the destination server is passed back to the client
 * unmodified.
 * <p>
 * If we went forward with this approach, various authentication and encryption schemes
 * could be devised, including ones that keep the proxy mostly blind to the contents of
 * the client's request. In the end, though, it's probably the wrong direction because it
 * starts re-implementing TLS. What's may be better is an end-to-end tunnel between the
 * client and the destination node, e.g. established via the usual HTTP CONNECT approach.
 * Then the proxy would be completely blind to the contents of the request and connections
 * would be kept alive by default (especially useful in Tor-land). Such a proxy
 * would handle the CONNECT request, establish the TCP connection via an internal Tor socks
 * proxy, and then route all future requests from the client through tor to the destination
 * node. See also: Tor2Web; WebTunnel.
 */
@Filter("/**")
public class ProxyFilter implements HttpFilter {

    public static final String X_FORWARD_TO = "X-Forward-To";

    private static final Logger log = HttpLog.log;

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(HttpRequest<?> request, FilterChain chain) {
        var targetHost = request.getHeaders().get(X_FORWARD_TO);
        if (targetHost == null) {
            // No proxying / forwarding has been requested by the client
            // Proceed normally, i.e. handle this request internally
            return chain.proceed(request);
        }

        var targetBaseUrl = "http://" + targetHost;

        log.debug("Forwarding request {} to {}", request, targetBaseUrl);

        // Forward the request to the target server
        HttpRequest<?> proxyRequest = HttpRequest.create(request.getMethod(), targetBaseUrl + request.getUri())
                .headers(entries -> request.getHeaders())
                .body(request.getBody().orElse(null));

        // Execute the request and return the response to the client
        return Mono.from(httpClient.exchange(proxyRequest))
                .flatMap(response -> Mono.just(HttpResponse.status(response.getStatus())
                        .headers(entries -> {
                            response.getHeaders().forEachValue(entries::set);
                            entries.remove(X_FORWARD_TO);
                            entries.set(HOST, targetHost);
                            entries.set(FORWARDED, request.getServerAddress().toString());
                        })
                        .body(response.getBody().orElse(null))));
    }
}
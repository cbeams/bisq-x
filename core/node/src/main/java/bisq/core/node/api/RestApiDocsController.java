package bisq.core.node.api;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.uri.UriBuilder;

import io.swagger.v3.oas.annotations.Hidden;

import java.net.URI;

/**
 * Root controller for requests to the {@link RestApiService}. Only handles redirecting
 * to api documentation. See other {@link Controller}-annotated classes throughout the
 * codebase for specific endpoint implementations.
 */
@Controller
class RestApiDocsController {

    final static URI APIDOC_URI = UriBuilder.of("/swagger-ui").path("index.html").build();

    /**
     * Redirect requests for / to the interactive api documentation.
     */
    @Get
    @Hidden
    HttpResponse<?> redirect() {
        return HttpResponse.seeOther(APIDOC_URI);
    }
}
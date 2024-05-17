package bisq.core.oas;

import bisq.core.api.ApiController;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.uri.UriBuilder;
import io.swagger.v3.oas.annotations.Hidden;

import java.net.URI;

@Controller
public class SwaggerUIController implements ApiController {

    private static final URI SWAGGER_UI_PATH = UriBuilder.of("/swagger-ui").path("index.html").build();

    @Get
    @Hidden
    HttpResponse<?> redirect() {
        return HttpResponse.seeOther(SWAGGER_UI_PATH);
    }
}

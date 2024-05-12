package bisq.core.api;

import bisq.core.domain.identity.api.IdentityController;
import bisq.core.domain.trade.api.OfferController;
import bisq.core.util.logging.api.LogController;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.uri.UriBuilder;

import io.micronaut.openapi.annotation.OpenAPIInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "bisq-openapi",
                version = "${bisq.version}"
        ),
        servers = @Server(url = "/")
)
@OpenAPIInclude(tags = @Tag(name = "Info"), classes = InfoController.class)
@OpenAPIInclude(tags = @Tag(name = "Logging"), classes = LogController.class)
@OpenAPIInclude(tags = @Tag(name = "Offer"), classes = OfferController.class)
@OpenAPIInclude(tags = @Tag(name = "User"), classes = IdentityController.class)
@Controller
public class InfoController {

    final static URI SWAGGER_UI_PATH = UriBuilder.of("/swagger-ui").path("index.html").build();

    @Get
    @Hidden
    HttpResponse<?> redirect() {
        return HttpResponse.seeOther(SWAGGER_UI_PATH);
    }

    @Get("/info")
    public Info getInfo() {
        return new Info("v2.1.0");
    }
}


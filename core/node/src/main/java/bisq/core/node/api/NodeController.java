package bisq.core.node.api;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.uri.UriBuilder;

import io.micronaut.openapi.annotation.OpenAPIInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;

@OpenAPIDefinition(
        info = @Info(
                title = "bisq-rest-api",
                version = "${bisq.version}"
        ),
        servers = @Server(url = "/")
)
@OpenAPIInclude(
        tags = @Tag(name = "Node"),
        classes = NodeController.class
)
@Controller
public class NodeController {

    final static URI SWAGGER_UI_PATH = UriBuilder.of("/swagger-ui").path("index.html").build();

    @Get
    @Hidden
    HttpResponse<?> redirect() {
        return HttpResponse.seeOther(SWAGGER_UI_PATH);
    }

    @Get("/info")
    public NodeInfo get() {
        return new NodeInfo("v2.1.0");
    }
}


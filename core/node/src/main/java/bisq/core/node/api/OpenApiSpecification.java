package bisq.core.node.api;

import bisq.core.trade.api.OfferController;

import io.micronaut.openapi.annotation.OpenAPIInclude;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "bisq-rest-api",
                version = "${bisq.version}"
        ),
        servers = @Server(url = "/")
)
@OpenAPIInclude(
        tags = @Tag(name = "Node"),
        classes = {
                NodeInfoController.class,
                LogController.class
        }
)
@OpenAPIInclude(
        tags = @Tag(name = "Trade"),
        classes = OfferController.class
)
interface OpenApiSpecification {
}

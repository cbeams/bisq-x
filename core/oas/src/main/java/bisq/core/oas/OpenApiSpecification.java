package bisq.core.oas;

import bisq.core.api.InfoController;
import bisq.core.domain.identity.api.IdentityController;
import bisq.core.domain.trade.api.OfferController;
import bisq.core.logging.api.LoggingController;

import io.micronaut.openapi.annotation.OpenAPIInclude;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.inject.Singleton;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "bisq-openapi",
                version = "${bisq.version}"
        ),
        servers = @Server(url = "/")
)
@OpenAPIInclude(tags = @Tag(name = "(Hidden)"), classes = SwaggerUIController.class)
@OpenAPIInclude(tags = @Tag(name = "Info"), classes = InfoController.class)
@OpenAPIInclude(tags = @Tag(name = "Logging"), classes = LoggingController.class)
@OpenAPIInclude(tags = @Tag(name = "Offer"), classes = OfferController.class)
@OpenAPIInclude(tags = @Tag(name = "User"), classes = IdentityController.class)
@Singleton
public class OpenApiSpecification {

    @SuppressWarnings("unused")
    private static void forStructure101() {
        var includes = new Class[]{
                InfoController.class,
                SwaggerUIController.class,
                LoggingController.class,
                OfferController.class,
                IdentityController.class,
        };
    }
}

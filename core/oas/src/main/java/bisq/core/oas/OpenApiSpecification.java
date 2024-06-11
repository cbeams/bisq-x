/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

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

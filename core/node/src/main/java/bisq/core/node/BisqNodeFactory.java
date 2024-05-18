package bisq.core.node;

import bisq.core.api.ApiController;
import bisq.core.domain.trade.OfferRepository;
import bisq.core.network.http.HttpServer;
import bisq.core.oas.OpenApiSpecification;
import io.micronaut.context.env.Environment;
import io.micronaut.runtime.Micronaut;

import java.util.Map;

class BisqNodeFactory {

    public static BisqNode buildWithOptions(Options options) {

        var dataDir = new DataDir(options);

        var context = BisqMicronaut.build(new String[]{})
                .banner(false)
                .properties(Map.of("micronaut.server.port", options.httpPort()))
                .start();

        return new BisqNode(
                options,
                dataDir,
                context.getBean(OfferRepository.class),
                context.getBean(HttpServer.class),
                context.getBeansOfType(ApiController.class),
                context.getBean(OpenApiSpecification.class)
        );
    }


    static class BisqMicronaut extends Micronaut {

        public static Micronaut build(String... args) {
            return (new BisqMicronaut()).args(args);
        }

        @Override
        protected void handleStartupException(Environment environment, Throwable exception) {
            // Micronaut calls System.exit in its default implementation
            // Rethrow instead so that enclosing Bisq app can handle it
            // see https://github.com/micronaut-projects/micronaut-core/issues/9147
            throw new RuntimeException(exception);
        }
    }
}

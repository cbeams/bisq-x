package bisq.core.node.api;

import io.micronaut.context.env.Environment;
import io.micronaut.runtime.Micronaut;

class BisqMicronaut extends Micronaut {

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

package bisq.core.logging.api;

import bisq.core.api.ApiController;

import bisq.core.logging.CategorySpec;
import bisq.core.logging.Logging;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;

import java.util.List;


@Controller("/logging")
public class LoggingController implements ApiController {

    @Get("/categories")
    public List<CategorySpec> getCategories() {
        return Logging.getCategorySpecs();
    }

    @Get("/categories/{name}")
    public CategorySpec getCategory(String name) {
        return Logging.getCategorySpec(name);
    }

    @Put("/categories")
    public void updateCategory(CategorySpec categorySpec) {
        Logging.update(categorySpec);
    }
}


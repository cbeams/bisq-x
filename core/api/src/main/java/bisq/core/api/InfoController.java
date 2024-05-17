package bisq.core.api;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller
public class InfoController implements ApiController {

    @Get("/info")
    public Info getInfo() {
        return new Info("v2.1.0");
    }
}
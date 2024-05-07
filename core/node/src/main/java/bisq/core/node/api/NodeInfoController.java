package bisq.core.node.api;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/info")
class NodeInfoController {

    @Get
    public NodeInfo get() {
        return new NodeInfo("v2.1.0");
    }
}


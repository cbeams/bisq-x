package bisq.core.domain.identity.api;

import bisq.core.domain.identity.Identity;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/identities")
public class IdentityController {

    @Post()
    public HttpResponse<?> create(Identity identity) {
        throw new UnsupportedOperationException("addIdentity is not yet supported");
    }
}
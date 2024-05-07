package bisq.core.trade.api;

import bisq.core.trade.Offer;
import bisq.core.util.Logging;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.slf4j.Logger;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller("/trade/offers")
public class OfferController {

    private static final Logger log = Logging.tradeLog;

    private final List<Offer> offers = new ArrayList<>();

    @Get
    public List<Offer> show() {
        return offers;
    }

    @Get("/{id}")
    public Offer show(String id) {
        return offers.stream().filter(offer -> offer.id().equals(id)).findFirst().orElseThrow();
    }

    @Post()
    public HttpResponse<?> add(Offer offer) {
        log.debug("Adding {}", offer);
        var id = UUID.randomUUID().toString();
        offers.add(new Offer(id, offer.details()));
        return HttpResponse.created(URI.create("/trade/offers/" + id));
    }
}


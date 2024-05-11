package bisq.core.domain.trade.api;

import bisq.core.domain.trade.Offer;
import bisq.core.domain.trade.OfferRepository;

import bisq.core.domain.trade.TradeLog;
import bisq.core.util.logging.Logging;
import org.slf4j.Logger;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static bisq.core.domain.trade.TradeLog.log;

@Controller("/trade/offers")
public class OfferController {

    private final OfferRepository offerRepository;

    public OfferController(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Get
    public List<Offer> show() {
        return offerRepository.findAll();
    }

    @Get("/{id}")
    public HttpResponse<Offer> show(String id) {
        var offer = offerRepository.findById(id);
        return offer.isPresent() ?
                HttpResponse.ok(offer.get()) :
                HttpResponse.notFound();
    }

    @Post()
    public HttpResponse<?> add(Offer offer) {
        log.debug("Adding {}", offer);
        var id = UUID.randomUUID().toString();
        offerRepository.save(new Offer(id, offer.details()));
        return HttpResponse.created(URI.create("/trade/offers/" + id));
    }
}

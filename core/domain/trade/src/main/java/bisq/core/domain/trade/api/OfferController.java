package bisq.core.domain.trade.api;

import bisq.core.api.ApiController;
import bisq.core.domain.trade.Offer;
import bisq.core.domain.trade.Offerbook;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import java.net.URI;
import java.util.List;

@Controller("/trade/offers")
public class OfferController implements ApiController {

    private final Offerbook offerbook;

    public OfferController(Offerbook offerbook) {
        this.offerbook = offerbook;
    }

    @Get
    public List<Offer> listAll() {
        return offerbook.findAll();
    }

    @Get("/{id}")
    public HttpResponse<Offer> show(String id) {
        var offer = offerbook.findById(id);
        return offer.isPresent() ?
                HttpResponse.ok(offer.get()) :
                HttpResponse.notFound();
    }

    @Post()
    public HttpResponse<?> add(Offer offer) {
        offerbook.save(Offer.withDetails(offer.details()));
        return HttpResponse.created(URI.create("/trade/offers/" + offer.id()));
    }

    @Delete("/{id}")
    public HttpResponse<Offer> delete(String id) {
        throw new UnsupportedOperationException("placeholder");
    }
}


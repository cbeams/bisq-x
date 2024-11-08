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

package bisq.core.domain.trading.offerbook.api;

import bisq.core.api.ApiController;
import bisq.core.domain.trading.offerbook.Offer;
import bisq.core.domain.trading.offerbook.Offerbook;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import java.net.URI;
import java.util.List;

@Controller("/offerbook")
public class OfferbookController implements ApiController {

    private final Offerbook offerbook;

    public OfferbookController(Offerbook offerbook) {
        this.offerbook = offerbook;
    }

    @Get
    public List<Offer> getOffers() {
        return offerbook.findAll();
    }

    @Get("/{offerId}")
    public HttpResponse<Offer> getOffer(String offerId) {
        var offer = offerbook.findById(offerId);
        return offer.isPresent() ?
                HttpResponse.ok(offer.get()) :
                HttpResponse.notFound();
    }

    @Post()
    public HttpResponse<?> addOffer(Offer offer) {
        offerbook.save(Offer.withDetails(offer.details()));
        return HttpResponse.created(URI.create("/offerbook/" + offer.id()));
    }

    @Delete("/{offerId}")
    public HttpResponse<Offer> removeOffer(String offerId) {
        throw new UnsupportedOperationException("placeholder");
    }
}


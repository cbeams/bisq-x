package bisq.core.domain.trade;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {
    List<Offer> findAll();

    Optional<Offer> findById(String id);

    void save(Offer offer);

    void addChangeCallback(Runnable callback);
}

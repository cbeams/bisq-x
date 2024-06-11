package bisq.core.domain.trade;

import bisq.core.network.p2p.P2PMessage;
import bisq.core.network.p2p.P2PMessage.Operation;
import bisq.core.network.p2p.P2PMessageListener;
import bisq.core.network.p2p.P2PMessageStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.inject.Singleton;

import static bisq.core.domain.trade.OfferCategory.log;

@Singleton
public class P2POfferbook implements P2PMessageListener, Offerbook {

    private static final String MESSAGE_TYPE = "offer";

    private final P2PMessageStore messageStore;

    private List<Offer> offers = new ArrayList<>();
    private ArrayList<Runnable> callbacks = new ArrayList<>();

    public P2POfferbook(P2PMessageStore messageStore) {
        this.messageStore = messageStore;
    }

    @Override
    public void onMessage(P2PMessage message, Operation operation) {
        if (!MESSAGE_TYPE.equals(message.type()))
            return;

        switch (operation) {
            case ADD -> {
                offers.add(new Offer(message.id(), message.body()));
                callbacks.forEach(Runnable::run);
            }
            case REMOVE -> {
                offers.removeIf(offer -> offer.id().equals(message.id()));
                callbacks.forEach(Runnable::run);
            }
        }
    }

    @Override
    public List<Offer> findAll() {
        return offers;
    }

    @Override
    public Optional<Offer> findById(String id) {
        return offers.stream().filter(offer -> offer.id().equals(id)).findFirst();
    }

    @Override
    public void save(Offer offer) {
        log.debug("Adding {}", offer);
        if (offers.stream().noneMatch(existing -> existing.id().equals(offer.id()))) {
            offers.add(offer);
            messageStore.add(new P2PMessage(MESSAGE_TYPE, offer.id(), offer.details()), this);
            callbacks.forEach(Runnable::run);
        }
    }

    @Override
    public void addChangeCallback(Runnable callback) {
        callbacks.add(callback);
    }
}
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

package bisq.core.domain.trading.offerbook;

import bisq.core.network.p2p.P2PMessage;
import bisq.core.network.p2p.P2PMessage.Operation;
import bisq.core.network.p2p.P2PMessageListener;
import bisq.core.network.p2p.P2PMessageStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.inject.Singleton;

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
        OfferbookFeature.log.debug("Adding {}", offer);
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

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

package bisq.core.network.p2p;

import java.util.ArrayList;

import jakarta.inject.Singleton;

import static bisq.core.network.p2p.P2PMessage.Operation.ADD;

@Singleton
public class P2PMessageStore {

    private ArrayList<P2PMessage> messages = new ArrayList<>();
    private ArrayList<P2PMessageListener> listeners = new ArrayList<>();

    public void add(P2PMessage message, P2PMessageListener source) {
        if (messages.stream().noneMatch(existing -> existing.id().equals(message.id()))) {
            messages.add(message);
            for (P2PMessageListener listener : listeners) {
                if (listener != source) {
                    listener.onMessage(message, ADD);
                }
            }
        }
    }
}

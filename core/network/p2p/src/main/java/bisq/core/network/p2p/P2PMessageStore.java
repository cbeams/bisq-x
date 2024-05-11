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

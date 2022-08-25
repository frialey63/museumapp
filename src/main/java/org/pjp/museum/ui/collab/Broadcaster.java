package org.pjp.museum.ui.collab;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.vaadin.flow.shared.Registration;

public final class Broadcaster {
    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

    private static final LinkedList<Consumer<MuseumMessage>> LISTENERS = new LinkedList<>();

    public static synchronized Registration register(Consumer<MuseumMessage> listener) {
        LISTENERS.add(listener);

        return () -> {
            synchronized (Broadcaster.class) {
                LISTENERS.remove(listener);
            }
        };
    }

    public static synchronized void broadcast(MuseumMessage message) {
        for (Consumer<MuseumMessage> listener : LISTENERS) {
            EXECUTOR.execute(() -> listener.accept(message));
        }
    }

    private Broadcaster() {
        // prevent instantiation
    }
}
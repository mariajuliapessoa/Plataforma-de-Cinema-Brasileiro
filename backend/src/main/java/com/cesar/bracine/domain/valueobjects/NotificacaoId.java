package com.cesar.bracine.domain.valueobjects;

import java.util.Objects;
import java.util.UUID;

public class NotificacaoId {
    private final UUID id;

    public NotificacaoId(UUID id) {
        this.id = id;
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NotificacaoId notificacaoId = (NotificacaoId) o;
        return Objects.equals(id, notificacaoId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

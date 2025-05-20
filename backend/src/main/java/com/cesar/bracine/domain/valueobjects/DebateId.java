package com.cesar.bracine.domain.valueobjects;

import java.util.Objects;
import java.util.UUID;

public class DebateId {
    private final UUID id;

    public DebateId(UUID id) {
        this.id = id;
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DebateId debateId = (DebateId) o;
        return Objects.equals(id, debateId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

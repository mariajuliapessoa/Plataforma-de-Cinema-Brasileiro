package com.cesar.bracine.domain.valueobjects;

import java.util.Objects;
import java.util.UUID;

public class ComentarioId {
    private final UUID id;

    public ComentarioId(UUID id) {
        this.id = id;
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ComentarioId comentarioId = (ComentarioId) o;
        return Objects.equals(id, comentarioId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

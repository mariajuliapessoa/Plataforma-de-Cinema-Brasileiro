package com.cesar.bracine.domain.valueobjects;

import java.util.Objects;
import java.util.UUID;

public class DesafioId {

    private final UUID id;

    public DesafioId(UUID id) {
        this.id = id;
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DesafioId desafioId = (DesafioId) o;
        return Objects.equals(id, desafioId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

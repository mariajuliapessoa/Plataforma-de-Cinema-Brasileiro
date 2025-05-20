package com.cesar.bracine.domain.valueobjects;

import java.util.Objects;
import java.util.UUID;

public class FilmeId {
    private final UUID id;

    public FilmeId(UUID id) {
        this.id = id;
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FilmeId filmeId = (FilmeId) o;
        return Objects.equals(id, filmeId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

package com.cesar.bracine.domain.valueobjects;

import java.util.Objects;
import java.util.UUID;

public class UsuarioId {
    private final UUID id;

    public UsuarioId(UUID id) {
        this.id = id;
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioId usuarioId = (UsuarioId) o;
        return Objects.equals(id, usuarioId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

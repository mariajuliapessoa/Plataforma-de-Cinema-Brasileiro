package com.cesar.bracine.infrastructure.jpa.entities;

import com.cesar.bracine.domain.enums.TipoNotificacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "notificacoes")
public class NotificacaoEntity {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private UsuarioEntity destinatario;

    @Column(nullable = false)
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacao tipoNotificacao;

    @Column(nullable = false)
    private Instant dataCriacao;

    @Column(nullable = false)
    private boolean lida;

    @PrePersist
    public void prePersist() {
        if (dataCriacao == null) {
            dataCriacao = Instant.now();
        }
    }

    public boolean getLida() {
        return lida;
    }
}

package com.cesar.bracine.infrastructure.mappers;

import com.cesar.bracine.domain.entities.Notificacao;
import com.cesar.bracine.infrastructure.jpa.entities.NotificacaoEntity;

public class NotificacaoMapper {

    public static NotificacaoEntity toEntity(Notificacao notificacao) {
        NotificacaoEntity entity = new NotificacaoEntity();
        entity.setId(notificacao.getId());
        entity.setDestinatario(UsuarioMapper.toEntity(notificacao.getDestinatario()));
        entity.setMensagem(notificacao.getMensagem());
        entity.setTipoNotificacao(notificacao.getTipo());
        entity.setDataCriacao(notificacao.getDataCriacao());
        entity.setLida(entity.getLida());

        return entity;
    }

    public static Notificacao toDomain(NotificacaoEntity entity) {
        return new Notificacao(
                entity.getId(),
                UsuarioMapper.toDomain(entity.getDestinatario()),
                entity.getMensagem(),
                entity.getTipoNotificacao(),
                entity.getDataCriacao(),
                entity.getLida()
        );
    }
}

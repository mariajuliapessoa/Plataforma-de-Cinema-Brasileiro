package com.cesar.bracine.infrastructure.mappers;

import com.cesar.bracine.domain.entities.Notificacao;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.valueobjects.NotificacaoId;
import com.cesar.bracine.infrastructure.jpa.entities.NotificacaoEntity;

public class NotificacaoMapper {

    public static NotificacaoEntity toEntity(Notificacao notificacao, Usuario usuario) {
        NotificacaoEntity entity = new NotificacaoEntity();
        entity.setId(notificacao.getId().getValue());
        entity.setDestinatario(UsuarioMapper.toEntity(usuario));
        entity.setMensagem(notificacao.getMensagem());
        entity.setTipoNotificacao(notificacao.getTipo());
        entity.setDataCriacao(notificacao.getDataCriacao());
        entity.setLida(entity.getLida());

        return entity;
    }

    public static Notificacao toDomain(NotificacaoEntity entity) {
        return new Notificacao(
                new NotificacaoId(entity.getId()),
                UsuarioMapper.toDomain(entity.getDestinatario()).getId(),
                entity.getMensagem(),
                entity.getTipoNotificacao(),
                entity.getDataCriacao(),
                entity.getLida()
        );
    }
}

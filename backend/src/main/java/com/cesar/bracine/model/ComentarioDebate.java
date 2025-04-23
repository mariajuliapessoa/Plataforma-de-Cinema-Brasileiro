package com.cesar.bracine.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "comentario_debate")
public class ComentarioDebate extends ComentarioBase {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discussion_id", nullable = false)
    private DebateFilme discussion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private ComentarioDebate parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComentarioDebate> replies = new ArrayList<>();

    private boolean reviewed = false;
    private boolean hidden = false;
    private boolean highlighted = false;
    private boolean expert = false;

    public void addReply(ComentarioDebate reply) {
        replies.add(reply);
    }

    public boolean isAuthorExpert() {
        return expert;
    }
}
package com.cesar.bracine.infrastructure.jpa.repository.template;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class RepositoryAbstratoImpl <T, E, ID, R  extends JpaRepository<E, ID>>{

    // T: Tipo Entidade, E: Entidade, ID: Identificador, R: Repositório

    protected final R jpaRepository;

    public RepositoryAbstratoImpl(R jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public void salvar(T domainEntity) {
        E entity = mapToEntity(domainEntity);
        jpaRepository.save(entity);
    }

    public Optional<T> buscarPorId(ID id) {
        Optional<E> entityOptional = jpaRepository.findById(id);

        if (entityOptional.isEmpty()) {
            logEntityNotFound(id);
            return Optional.empty();
        }

        T domainEntity = mapToDomain(entityOptional.get());
        return Optional.of(domainEntity);
    }

    public List<T> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    public void remover(ID id) {
        jpaRepository.deleteById(id);
    }

    protected abstract E mapToEntity(T domainEntity);
    protected abstract T mapToDomain(E entity);

    protected void logEntityNotFound(ID id) {
        System.out.println("Entidade com ID " + id + " não encontrada no banco.");
    }
}

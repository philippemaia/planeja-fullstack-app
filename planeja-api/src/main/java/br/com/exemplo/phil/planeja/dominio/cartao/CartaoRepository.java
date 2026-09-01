package br.com.exemplo.phil.planeja.dominio.cartao;

import br.com.exemplo.phil.planeja.dominio.cartao.model.CartaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartaoRepository extends JpaRepository<CartaoEntity, UUID> {

    Optional<CartaoEntity> findByNome(String nome);

    @Query("""
        SELECT c
        FROM CartaoEntity c
        WHERE ( :id is null OR c.id != :id )
            AND c.nome = :nome
    """)
    List<CartaoEntity> findByNomeAndNotId(@Param("nome") String nome, @Param("id") UUID id);
}

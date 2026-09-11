package br.com.exemplo.phil.planeja.dominio.categoria;

import br.com.exemplo.phil.planeja.dominio.categoria.model.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, UUID> {

    // select exists (select 1 from categoria where nome = ?1 )
    boolean existsByNome(String nome);
}

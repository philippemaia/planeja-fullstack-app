package br.com.exemplo.phil.planeja.dominio.categoria.mapper;

import br.com.exemplo.phil.planeja.dominio.categoria.dto.CategoriaDetalhes;
import br.com.exemplo.phil.planeja.dominio.categoria.dto.CategoriaForm;
import br.com.exemplo.phil.planeja.dominio.categoria.model.CategoriaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaEntity toEntity(CategoriaForm nova);
    CategoriaDetalhes toDetalhes(CategoriaEntity entity);
}

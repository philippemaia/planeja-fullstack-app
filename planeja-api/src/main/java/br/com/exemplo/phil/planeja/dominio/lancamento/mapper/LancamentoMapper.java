package br.com.exemplo.phil.planeja.dominio.lancamento.mapper;

import br.com.exemplo.phil.planeja.dominio.cartao.model.CartaoEntity;
import br.com.exemplo.phil.planeja.dominio.categoria.model.CategoriaEntity;
import br.com.exemplo.phil.planeja.dominio.lancamento.dto.LancamentoDetalhes;
import br.com.exemplo.phil.planeja.dominio.lancamento.dto.LancamentoForm;
import br.com.exemplo.phil.planeja.dominio.lancamento.model.LancamentoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LancamentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", source = "categoria")
    @Mapping(target = "cartao", source = "cartao")
    LancamentoEntity toEntity(LancamentoForm form, CategoriaEntity categoria, CartaoEntity cartao);

    @Mapping(target = "categoriaId", source = "categoria.id")
    @Mapping(target = "categoriaNome", source = "categoria.nome")
    @Mapping(target = "cartaoId", source = "cartao.id")
    @Mapping(target = "cartaoNome", source = "cartao.nome")
    LancamentoDetalhes toDetalhes(LancamentoEntity entity);
}

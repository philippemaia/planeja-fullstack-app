package br.com.exemplo.phil.planeja.dominio.cartao.mapper;

import br.com.exemplo.phil.planeja.dominio.cartao.dto.CartaoDetalhes;
import br.com.exemplo.phil.planeja.dominio.cartao.dto.CartaoForm;
import br.com.exemplo.phil.planeja.dominio.cartao.model.CartaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartaoMapper {

    CartaoEntity toEntity(CartaoForm form);

    CartaoDetalhes toDetalhes(CartaoEntity entity);

    void update(@MappingTarget CartaoEntity entity, CartaoForm dadosAtualizacao);
}

package br.com.exemplo.phil.planeja.dominio.cartao;

import br.com.exemplo.phil.planeja.common.exceptions.RegistroNaoEncontradoException;
import br.com.exemplo.phil.planeja.common.exceptions.ValidationException;
import br.com.exemplo.phil.planeja.dominio.cartao.dto.CartaoDetalhes;
import br.com.exemplo.phil.planeja.dominio.cartao.dto.CartaoForm;
import br.com.exemplo.phil.planeja.dominio.cartao.mapper.CartaoMapper;
import br.com.exemplo.phil.planeja.dominio.cartao.model.CartaoEntity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CartaoService {

    @Autowired
    private CartaoValidator validator;

    @Autowired
    private CartaoRepository repository;

    @Autowired
    private CartaoMapper mapper;

    public CartaoDetalhes criar(CartaoForm form){
        var result = validator.validar(form, null);

        if(result.isInvalido()){
            throw new ValidationException(result.getCampoInvalidos());
        }

        CartaoEntity entity = mapper.toEntity(form);
        repository.save(entity);
        return mapper.toDetalhes(entity);
    }

    public CartaoDetalhes obterDetalhes(UUID id){
        return repository.findById(id)
                .map(mapper::toDetalhes)
                .orElseThrow(RegistroNaoEncontradoException::new);
    }

    @Transactional
    public void atualizar(UUID id, CartaoForm dadosAtualizacao) {
        var entity = repository.findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new);

        var result = validator.validar(dadosAtualizacao, id);

        if(result.isInvalido()){
            throw new ValidationException(result.getCampoInvalidos());
        }

        mapper.update(entity, dadosAtualizacao);

        repository.save(entity);
    }

    public Page<CartaoDetalhes> listar(PageRequest pageRequest){
        return repository
                .findAll(pageRequest)
                .map(mapper::toDetalhes);
    }

    @Transactional
    public void mudarStatus(UUID id){
        var cartao = repository.findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new);

        cartao.setAtivo(!cartao.getAtivo());

        repository.save(cartao); // opcional
    }
}

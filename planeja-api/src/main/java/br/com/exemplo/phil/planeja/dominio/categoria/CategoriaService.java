package br.com.exemplo.phil.planeja.dominio.categoria;

import br.com.exemplo.phil.planeja.common.exceptions.RegistroNaoEncontradoException;
import br.com.exemplo.phil.planeja.common.exceptions.ValidationException;
import br.com.exemplo.phil.planeja.common.validation.ValidationResult;
import br.com.exemplo.phil.planeja.dominio.categoria.dto.CategoriaDetalhes;
import br.com.exemplo.phil.planeja.dominio.categoria.dto.CategoriaForm;
import br.com.exemplo.phil.planeja.dominio.categoria.mapper.CategoriaMapper;
import br.com.exemplo.phil.planeja.dominio.categoria.model.CategoriaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaValidator validator;

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private CategoriaMapper mapper;

    public CategoriaDetalhes criar( CategoriaForm nova) {
        ValidationResult result = validator.validar(nova);

        if(result.isInvalido()){
            throw new ValidationException(result.getCampoInvalidos());
        }

        CategoriaEntity entity = mapper.toEntity(nova);
        repository.save(entity);

        return mapper.toDetalhes(entity);
    }

    public Page<CategoriaDetalhes> listar(PageRequest pageRequest) {
        return repository
                .findAll(pageRequest)
                .map(mapper::toDetalhes);
    }

    @Transactional
    public void mudarStatus(UUID id) {
        var categoria = repository.findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new);

        categoria.setAtivo(!categoria.getAtivo());
        repository.save(categoria); // opcional
    }
}

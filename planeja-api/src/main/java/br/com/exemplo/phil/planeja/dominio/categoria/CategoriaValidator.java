package br.com.exemplo.phil.planeja.dominio.categoria;

import br.com.exemplo.phil.planeja.common.validation.CampoInvalido;
import br.com.exemplo.phil.planeja.common.validation.ValidationResult;
import br.com.exemplo.phil.planeja.dominio.categoria.dto.CategoriaForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoriaValidator {

    @Autowired
    private CategoriaRepository repository;

    public ValidationResult validar(CategoriaForm nova) {

        var result = ValidationResult.novo();

        var existeCategoriaCadastrada = repository.existsByNome(nova.nome());

        if(existeCategoriaCadastrada){
            result.add(new CampoInvalido("nome", "Existe uma categoria já cadastrada com este nome."));
        }

        return result;
    }
}

package br.com.exemplo.phil.planeja.dominio.cartao;

import br.com.exemplo.phil.planeja.common.validation.CampoInvalido;
import br.com.exemplo.phil.planeja.common.validation.ValidationResult;
import br.com.exemplo.phil.planeja.dominio.cartao.dto.CartaoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CartaoValidator {

    @Autowired
    private CartaoRepository repository;

    public ValidationResult validar(CartaoForm form, UUID id){
        var result = ValidationResult.novo();

        var isListaNaoVazia = !repository.findByNomeAndNotId(form.nome(), id).isEmpty();
        if(isListaNaoVazia){
            result.add(new CampoInvalido("nome", "Já cadastrado."));
        }

        return result;
    }
}

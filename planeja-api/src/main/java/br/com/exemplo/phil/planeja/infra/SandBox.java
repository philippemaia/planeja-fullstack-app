package br.com.exemplo.phil.planeja.infra;

import br.com.exemplo.phil.planeja.dominio.cartao.CartaoRepository;
import br.com.exemplo.phil.planeja.dominio.cartao.model.BandeiraCartao;
import br.com.exemplo.phil.planeja.dominio.cartao.model.CartaoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SandBox implements CommandLineRunner {

    @Autowired
    private CartaoRepository cartaoRepository;

    public void salvarCartao(){
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNome("ITAU Personalité");
        cartao.setBandeira(BandeiraCartao.AMERICAN_EXPRESS);

        cartaoRepository.save(cartao);
    }

    @Override
    public void run(String... args) throws Exception {
//        salvarCartao();
    }
}

package br.com.exemplo.phil.planeja.dominio.lancamento;

import br.com.exemplo.phil.planeja.dominio.cartao.CartaoService;
import br.com.exemplo.phil.planeja.dominio.cartao.dto.CartaoDetalhes;
import br.com.exemplo.phil.planeja.dominio.categoria.CategoriaService;
import br.com.exemplo.phil.planeja.dominio.categoria.dto.CategoriaDetalhes;
import br.com.exemplo.phil.planeja.dominio.lancamento.dto.LancamentoDetalhes;
import br.com.exemplo.phil.planeja.dominio.lancamento.dto.LancamentoForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lancamentos")
public class CadastroLancamentoController {

    @Autowired
    private LancamentoService service;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CartaoService cartaoService;


//    @PostMapping
//    public ResponseEntity<LancamentoDetalhes> criar(@RequestBody @Valid LancamentoForm form){
//        var detalhes = service.criar(form);
//        return ResponseEntity.status(HttpStatus.CREATED).body(detalhes);
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LancamentoDetalhes criar(@RequestBody @Valid LancamentoForm form){
        return service.criar(form);
    }

    @GetMapping("categorias-disponiveis")
    public List<CategoriaDetalhes>  listarCategoriasDisponiveis(){
        return categoriaService.listarAtivas();
    }

    @GetMapping("cartoes-disponiveis")
    public List<CartaoDetalhes>  listarCartoesDisponiveis(){
        return cartaoService.listarAtivos();
    }
}

package br.com.exemplo.phil.planeja.dominio.lancamento;

import br.com.exemplo.phil.planeja.common.exceptions.RegistroNaoEncontradoException;
import br.com.exemplo.phil.planeja.common.exceptions.ValidationException;
import br.com.exemplo.phil.planeja.dominio.cartao.CartaoRepository;
import br.com.exemplo.phil.planeja.dominio.cartao.model.CartaoEntity;
import br.com.exemplo.phil.planeja.dominio.categoria.CategoriaRepository;
import br.com.exemplo.phil.planeja.dominio.categoria.model.CategoriaEntity;
import br.com.exemplo.phil.planeja.dominio.lancamento.dto.LancamentoDetalhes;
import br.com.exemplo.phil.planeja.dominio.lancamento.dto.LancamentoForm;
import br.com.exemplo.phil.planeja.dominio.lancamento.mapper.LancamentoMapper;
import br.com.exemplo.phil.planeja.dominio.lancamento.model.LancamentoEntity;
import br.com.exemplo.phil.planeja.dominio.lancamento.model.TipoLancamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.UUID;

import static br.com.exemplo.phil.planeja.dominio.lancamento.LancamentoSpecs.*;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private LancamentoValidator validator;

    @Autowired
    private LancamentoMapper mapper;

    public LancamentoDetalhes criar(LancamentoForm form){

        CategoriaEntity categoria = categoriaRepository
                                        .findById(form.categoriaId())
                                        .orElse(null);

        CartaoEntity cartao = null;
        if(form.cartaoId() != null){
            cartao = cartaoRepository
                            .findById(form.cartaoId())
                            .orElse(null);
        }

        var result = validator.validar(form,  categoria, cartao);
        if(result.isInvalido()){
            throw new ValidationException(result.getCampoInvalidos());
        }

        LancamentoEntity entity = mapper.toEntity(form, categoria, cartao);

        lancamentoRepository.save(entity);

        return mapper.toDetalhes(entity);
    }

    public Page<LancamentoDetalhes> listar(PageRequest pageRequest, YearMonth mes, TipoLancamento tipo, UUID categoriaId){

        // select * from LancamentoEntity where 1 = 1
        Specification<LancamentoEntity> spec = Specification.unrestricted();

        if(tipo != null){
            // and tipo = :tipo
            spec = spec.and(tipoEqual(tipo));
        }

        if(categoriaId != null){
            spec = spec.and(categoriaEqual(categoriaId)); // and categoriaId = :categoriaId
        }

        if(mes != null){
            spec = spec.and(mesEqual(mes)); // and mes = :mes
        }

        var resultado = lancamentoRepository.findAll(spec, pageRequest);

        return resultado.map(mapper::toDetalhes);
    }

    public void deletar(UUID id){
        var lancamento = lancamentoRepository.findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new);

        lancamentoRepository.delete(lancamento);
    }
}

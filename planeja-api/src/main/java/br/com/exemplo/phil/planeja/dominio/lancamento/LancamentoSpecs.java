package br.com.exemplo.phil.planeja.dominio.lancamento;

import br.com.exemplo.phil.planeja.dominio.lancamento.model.LancamentoEntity;
import br.com.exemplo.phil.planeja.dominio.lancamento.model.TipoLancamento;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;

public class LancamentoSpecs {

    private LancamentoSpecs(){}

    public static Specification<LancamentoEntity> tipoEqual(TipoLancamento tipo){
        return (root, criteriaQuery, cb) -> {

            //LancamentoEntity.tipo = :tipo

            return cb.equal(root.get("tipo"), tipo);
        };
    }

    public static Specification<LancamentoEntity> categoriaEqual(UUID categoriaId){
        return (root, criteriaQuery, cb) ->  cb.equal(root.get("categoria").get("id"), categoriaId);
    }

    public static Specification<LancamentoEntity> mesEqual(YearMonth mes){
        return (root, criteriaQuery, cb) -> {
            LocalDate inicioMes = mes.atDay(1);
            LocalDate fimMes = mes.atEndOfMonth();

            return cb.between(root.get("data"), inicioMes, fimMes);
        };
    }
}

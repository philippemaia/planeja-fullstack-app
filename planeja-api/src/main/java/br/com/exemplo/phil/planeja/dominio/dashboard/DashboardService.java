package br.com.exemplo.phil.planeja.dominio.dashboard;

import br.com.exemplo.phil.planeja.dominio.dashboard.dto.Dashboard;
import br.com.exemplo.phil.planeja.dominio.dashboard.dto.DespesaCategoriaResumo;
import br.com.exemplo.phil.planeja.dominio.dashboard.dto.ResumoFinanceiro;
import br.com.exemplo.phil.planeja.dominio.lancamento.LancamentoRepository;
import br.com.exemplo.phil.planeja.dominio.lancamento.model.TipoLancamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public Dashboard obterDashboardMesAtual(){
        YearMonth mesAtual = YearMonth.now();
        LocalDate primeiroDiaMes = mesAtual.atDay(1);
        LocalDate ultimoDia = mesAtual.atEndOfMonth();

        BigDecimal receitas = lancamentoRepository.somarPorTipoNoPeriodo(TipoLancamento.RECEITA, primeiroDiaMes, ultimoDia);
        BigDecimal despesas = lancamentoRepository.somarPorTipoNoPeriodo(TipoLancamento.DESPESA, primeiroDiaMes, ultimoDia);;
        BigDecimal saldo = receitas.subtract(despesas);

        ResumoFinanceiro  resumoFinanceiro  = new ResumoFinanceiro(receitas, despesas, saldo);

        List<DespesaCategoriaResumo> despesasPorCategoria = lancamentoRepository
                .somaPorCategoriaNoPeriodo(TipoLancamento.DESPESA, primeiroDiaMes, ultimoDia)
                .stream()
                .map( resultado ->
                        new DespesaCategoriaResumo(
                                (String) resultado[0],
                                (BigDecimal) resultado[1])
                ).toList();

        return new Dashboard(
                mesAtual.toString(),
                resumoFinanceiro,
                despesasPorCategoria);
    }
}

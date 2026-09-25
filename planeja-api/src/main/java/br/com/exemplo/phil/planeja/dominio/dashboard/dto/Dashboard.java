package br.com.exemplo.phil.planeja.dominio.dashboard.dto;

import java.util.List;

public record Dashboard(
        String mes,
        ResumoFinanceiro resumo,
        List<DespesaCategoriaResumo> despesasPorCategoria
) {
}

package br.com.exemplo.phil.planeja.dominio.dashboard.dto;

import java.math.BigDecimal;

public record DespesaCategoriaResumo(
        String categoria,
        BigDecimal valor
) {
}

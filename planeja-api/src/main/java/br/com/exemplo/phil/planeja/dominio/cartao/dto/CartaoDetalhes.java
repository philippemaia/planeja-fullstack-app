package br.com.exemplo.phil.planeja.dominio.cartao.dto;

import br.com.exemplo.phil.planeja.dominio.cartao.model.BandeiraCartao;

import java.time.LocalDateTime;

public record CartaoDetalhes(
        String id,
        String nome,
        BandeiraCartao bandeira,
        LocalDateTime dataCadastro,
        Boolean ativo) {
}

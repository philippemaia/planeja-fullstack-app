package br.com.exemplo.phil.planeja.dominio.usuario.dto;

public record AuthResponse(
        String token,
        String tipo,
        long expiraEm,
        String nome
) {
}

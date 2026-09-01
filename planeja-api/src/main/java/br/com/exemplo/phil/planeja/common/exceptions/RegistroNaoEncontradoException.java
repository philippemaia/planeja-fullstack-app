package br.com.exemplo.phil.planeja.common.exceptions;

public class RegistroNaoEncontradoException extends RuntimeException {

    public RegistroNaoEncontradoException() {
        super("Registro não encontrado.");
    }
}

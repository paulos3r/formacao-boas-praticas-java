package br.com.alura.adopet.api.exception;

public class ValidacaoException extends RuntimeException {

  public ValidacaoException(String menssagem) {
    super(menssagem);
  }
}

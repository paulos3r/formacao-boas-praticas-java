package br.com.alura.adopet.api.validacoes;


import br.com.alura.adopet.api.dto.CadastroAbrigoDto;

public interface IValidacaoCadastrarAbrigo {
  void validar(CadastroAbrigoDto dto);
}

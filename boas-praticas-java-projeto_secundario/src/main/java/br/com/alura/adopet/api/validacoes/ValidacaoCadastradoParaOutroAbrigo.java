package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.CadastrarAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidacaoCadastradoParaOutroAbrigo implements IValidacaoCadastrarAbrigo{

  @Autowired
  private AbrigoRepository abrigoRepository;

  @Override
  public void validar(CadastrarAbrigoDto dto) {

    boolean nomeJaCadastrado = abrigoRepository.existsByNome(dto.nome());
    boolean telefoneJaCadastrado = abrigoRepository.existsByTelefone(dto.telefone());
    boolean emailJaCadastrado = abrigoRepository.existsByEmail(dto.email());

    if (nomeJaCadastrado || telefoneJaCadastrado || emailJaCadastrado) {
      throw new ValidacaoException( "Dados já cadastrados para outro abrigo!" );
    }
  }
}

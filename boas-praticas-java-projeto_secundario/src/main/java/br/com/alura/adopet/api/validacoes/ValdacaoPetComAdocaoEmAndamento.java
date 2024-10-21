package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValdacaoPetComAdocaoEmAndamento implements IValidacaoSolicitacaoAdocao {

  @Autowired
  private AdocaoRepository repository;

  public void validar(SolicitacaoAdocaoDto dto){

    boolean petTemAdocaoEmAnadamento = repository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO);

    if (petTemAdocaoEmAnadamento) {
        throw new ValidacaoException("Pet já está aguardando avaliação para ser adotado!");
    }
  }

}

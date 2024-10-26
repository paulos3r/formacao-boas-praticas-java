package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastrarAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.validacoes.IValidacaoCadastrarAbrigo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbrigoService {

  @Autowired
  private AbrigoRepository abrigoRepository;

  @Autowired
  private List<IValidacaoCadastrarAbrigo> validacao;

  public void cadastrar(CadastrarAbrigoDto dto){

    validacao.forEach(v -> v.validar(dto));

    Abrigo abrigo = new Abrigo(dto.nome(), dto.telefone(), dto.email());

    abrigoRepository.save(abrigo);

  }
  public void listarPets(String idOuNome){

    try {
      Long id = Long.parseLong(idOuNome);
      List<Pet> pets = abrigoRepository.getReferenceById(id).getPets();
    } catch (EntityNotFoundException enfe) {
      throw new ValidacaoException(enfe.getMessage() );
    } catch (NumberFormatException e) {
      try {
        List<Pet> pets = abrigoRepository.findByNome(idOuNome).getPets();
      } catch (EntityNotFoundException enfe) {
        throw new ValidacaoException(enfe.getMessage() );
      }
    }

  }
  public void cadastrarPets(String idOuNome, Pet pet){
    // id ou nome do abrigo || pet cadastro do pet inteiro vou pegar o cadastro e vincular no abrigo ( pet no abrigo )
    try {
      Long id = Long.parseLong(idOuNome);
      Abrigo abrigo = abrigoRepository.getReferenceById(id);

      pet.setAbrigo(abrigo);
      pet.setAdotado(false);
      abrigo.getPets().add(pet);



      abrigoRepository.save(abrigo);
    } catch (EntityNotFoundException enfe) {
      throw new ValidacaoException(enfe.getMessage() );
    } catch (NumberFormatException nfe) {
      try {
        Abrigo abrigo = abrigoRepository.findByNome(idOuNome);

        pet.setAbrigo(abrigo);
        pet.setAdotado(false);
        abrigo.getPets().add(pet);

        abrigoRepository.save(abrigo);
      } catch (EntityNotFoundException enfe) {
        throw new ValidacaoException(enfe.getMessage());
      }
    }
  }
}

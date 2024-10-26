package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastrarAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbrigoService {

  @Autowired
  private AbrigoRepository abrigoRepository;

  public void cadastrar(CadastrarAbrigoDto dto){

    boolean nomeJaCadastrado = abrigoRepository.existsByNome(dto.nome());
    boolean telefoneJaCadastrado = abrigoRepository.existsByTelefone(dto.telefone());
    boolean emailJaCadastrado = abrigoRepository.existsByEmail(dto.email());

    if (nomeJaCadastrado || telefoneJaCadastrado || emailJaCadastrado) {
      throw new ValidacaoException( "Dados já cadastrados para outro abrigo!" );
    }

    Abrigo abrigo = new Abrigo(dto.nome(), dto.telefone(), dto.email());

    abrigoRepository.save(abrigo);

  }
  public void listarPets(String idOuNome){

    try {
      Long id = Long.parseLong(idOuNome);
      List<Pet> pets = abrigoRepository.getReferenceById(id).getPets();
      // return ResponseEntity.ok(pets);
    } catch (EntityNotFoundException enfe) {
      //return ResponseEntity.notFound().build();
      throw new ValidacaoException(enfe.getMessage() );
    } catch (NumberFormatException e) {
      try {
        List<Pet> pets = abrigoRepository.findByNome(idOuNome).getPets();
        //return ResponseEntity.ok(pets);
      } catch (EntityNotFoundException enfe) {
        //return ResponseEntity.notFound().build();
        throw new ValidacaoException(enfe.getMessage() );
      }
    }

  }
  public void cadastrarPets(String idOuNome, Pet pet){
    try {
      Long id = Long.parseLong(idOuNome);
      Abrigo abrigo = abrigoRepository.getReferenceById(id);
      pet.setAbrigo(abrigo);
      pet.setAdotado(false);
      abrigo.getPets().add(pet);
      abrigoRepository.save(abrigo);
      //return ResponseEntity.ok().build();
    } catch (EntityNotFoundException enfe) {
      //return ResponseEntity.notFound().build();
      throw new ValidacaoException(enfe.getMessage() );
    } catch (NumberFormatException nfe) {
      try {
        Abrigo abrigo = abrigoRepository.findByNome(idOuNome);
        pet.setAbrigo(abrigo);
        pet.setAdotado(false);
        abrigo.getPets().add(pet);
        abrigoRepository.save(abrigo);
        //return ResponseEntity.ok().build();
      } catch (EntityNotFoundException enfe) {
        //return ResponseEntity.notFound().build();
        throw new ValidacaoException(enfe.getMessage());
      }
    }
  }
}

package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.IValidacaoSolicitacaoAdocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdocaoService {

  @Autowired
  private AdocaoRepository repository;

  @Autowired
  private PetRepository petRepository;

  @Autowired
  private TutorRepository tutorRepository;

  @Autowired
  private EmailService emailService;

  @Autowired
  private List<IValidacaoSolicitacaoAdocao> validacao;

  public void solicitar(SolicitacaoAdocaoDto dto){
    Pet pet = petRepository.getReferenceById(dto.idPet());
    Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

    validacao.forEach(v->v.validar(dto));

    Adocao adocao = new Adocao(tutor,pet, dto.motivo());

    repository.save(adocao);

    String mensagem = "Olá " +adocao.getPet().getAbrigo().getNome() +"!\n\nUma solicitação de adoção foi registrada hoje para o pet: " +adocao.getPet().getNome() +". \nFavor avaliar para aprovação ou reprovação.";
    emailService.enviarEmail(adocao.getPet().getAbrigo().getEmail(),"Solicitação de adoção",mensagem);
  }

  public void aprovar(AprovacaoAdocaoDto dto){
    Adocao adocao = repository.getReferenceById(dto.idAdocao());

    adocao.marcarComoAprovado();

    String mensagem = "Parabéns " +adocao.getTutor().getNome() +"!\n\nSua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi aprovada.\nFavor entrar em contato com o abrigo " +adocao.getPet().getAbrigo().getNome() +" para agendar a busca do seu pet.";
    emailService.enviarEmail(adocao.getPet().getAbrigo().getEmail(),"Adoção aprovada",mensagem );

  }
  public void reprovar(ReprovacaoAdocaoDto dto){
    Adocao adocao = repository.getReferenceById(dto.idAdocao());

    adocao.marcarComoReprovado(dto.justificativa());

    String mensagem = "Olá " +adocao.getTutor().getNome() +"!\n\nInfelizmente sua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi reprovada pelo abrigo " +adocao.getPet().getAbrigo().getNome() +" com a seguinte justificativa: " +adocao.getJustificativaStatus();
    emailService.enviarEmail(adocao.getPet().getAbrigo().getEmail(),"Adoção reprovada", mensagem );
  }
}

package br.com.alura;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.service.PetService;

import java.io.IOException;

public class ListarPetsAbrigoCommand implements Command {
  @Override
  public void execute() {
    try {
      ClientHttpConfiguration clint = new ClientHttpConfiguration();
      PetService petService = new PetService(clint);

      petService.listarPetsAbrigo();

    }catch (IOException | InterruptedException e){
      System.out.println(e.getMessage());
    }
  }
}

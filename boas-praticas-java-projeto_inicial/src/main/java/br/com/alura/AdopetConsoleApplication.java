package br.com.alura;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.service.AbrigoService;
import br.com.alura.service.PetService;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class AdopetConsoleApplication {

  public static void main(String[] args) {
    ClientHttpConfiguration clint = new ClientHttpConfiguration();

    AbrigoService abrigoService = new AbrigoService(clint);
    PetService petService = new PetService(clint);

    System.out.println("##### BOAS VINDAS AO SISTEMA ADOPET CONSOLE #####");
    try {
      int opcaoEscolhida = 0;
      while (opcaoEscolhida != 5) {
        System.out.println("\nDIGITE O NÚMERO DA OPERAÇÃO DESEJADA:");
        System.out.println("1 -> Listar abrigos cadastrados");
        System.out.println("2 -> Cadastrar novo abrigo");
        System.out.println("3 -> Listar pets do abrigo");
        System.out.println("4 -> Importar pets do abrigo");
        System.out.println("5 -> Sair");

        String textoDigitado = new Scanner(System.in).nextLine();
        opcaoEscolhida = Integer.parseInt(textoDigitado);

        if (opcaoEscolhida == 1) {
          abrigoService.listarAbrigo();
        } else if (opcaoEscolhida == 2) {
          abrigoService.cadastrarAbrigo();
        } else if (opcaoEscolhida == 3) {
          petService.listarPetsAbrigo();
        } else if (opcaoEscolhida == 4) {
          petService.importarPetsAbrigo();
        } else if (opcaoEscolhida == 5) {
          break;
        } else {
          System.out.println("NÚMERO INVÁLIDO!");
          opcaoEscolhida = 0;
        }
      }
      System.out.println("Finalizando o programa...");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  private static HttpResponse<String> dispararRequisicaoGet(HttpClient client, String uri) throws IOException, InterruptedException {

            .uri(URI.create(uri))
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();
    return client.send(request, HttpResponse.BodyHandlers.ofString());
  }
  private static HttpResponse<String> dispararRequisicaoPost(HttpClient client, String uri, JsonObject json) throws IOException, InterruptedException {

            .uri(URI.create(uri))
            .header("Content-Type", "application/json")
            .method("POST", HttpRequest.BodyPublishers.ofString(json.toString()))
            .build();

     return client.send(request, HttpResponse.BodyHandlers.ofString());
  }
}

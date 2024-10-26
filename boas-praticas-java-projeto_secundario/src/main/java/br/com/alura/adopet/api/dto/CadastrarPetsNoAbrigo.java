package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Pet;

import java.util.List;

public record CadastrarPetsNoAbrigo(List<Pet> pets) {
}

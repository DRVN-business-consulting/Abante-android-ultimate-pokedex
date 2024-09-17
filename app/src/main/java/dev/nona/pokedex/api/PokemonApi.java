package dev.nona.pokedex.api;

import java.util.List;

import dev.nona.pokedex.model.dto.response.PokemonResponseDto;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PokemonApi {
    @GET("pokemon")
    Call<List<PokemonResponseDto>> getPokemons();
}

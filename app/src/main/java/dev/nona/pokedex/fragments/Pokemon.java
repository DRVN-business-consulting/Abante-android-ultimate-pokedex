package dev.nona.pokedex.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import dev.nona.pokedex.R;
import dev.nona.pokedex.adapter.PokemonAdapter;
import dev.nona.pokedex.api.API;
import dev.nona.pokedex.model.dto.response.ErrorDto;
import dev.nona.pokedex.model.dto.response.PokemonResponseDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pokemon extends Fragment {

    RecyclerView recyclerView;
    PokemonAdapter adapter;

    public Pokemon() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pokemon, container, false);
        recyclerView = view.findViewById(R.id.rvpokemonList);

        // Initialize RecyclerView and set the layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        // Fetch Pokémon data and set the adapter
        fetchPokemonData(view);

        return view;
    }

    private void fetchPokemonData(View view) {
        API.pokemonApi().getPokemons().enqueue(new Callback<List<PokemonResponseDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<PokemonResponseDto>> call, @NonNull Response<List<PokemonResponseDto>> response) {
                if (response.isSuccessful()) {
                    List<PokemonResponseDto> pokemonDto = response.body();
                    if (pokemonDto != null) {
                        // Initialize the adapter with the fetched data
                        adapter = new PokemonAdapter(pokemonDto);
                        recyclerView.setAdapter(adapter); // Set the adapter after data is ready
                    }
                } else {
                    // Handle the error case
                    ResponseBody errorBody = null;
                    try {
                        errorBody = response.errorBody();
                        if (errorBody != null) {
                            String json = errorBody.string();
                            ErrorDto errorDto = API.gson.fromJson(json, ErrorDto.class);
                            Toast.makeText(view.getContext(), errorDto.getDetail(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (errorBody != null) {
                            errorBody.close();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PokemonResponseDto>> call, @NonNull Throwable throwable) {
                // Handle failure (e.g., network issues)
                Toast.makeText(view.getContext(), "Failed to load Pokémon data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

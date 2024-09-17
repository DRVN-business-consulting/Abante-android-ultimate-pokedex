package dev.nona.pokedex.model.dto.response;

import java.util.List;

import dev.nona.pokedex.model.model.Base;
import dev.nona.pokedex.model.model.Evolution;
import dev.nona.pokedex.model.model.Image;
import dev.nona.pokedex.model.model.Name;
import dev.nona.pokedex.model.model.Profile;

public class PokemonResponseDto {
    public int id;
    public Name name;
    public List<String> type;
    public Base base;
    public String species;
    public String description;
    public Evolution evolution;
    public Profile profile;
    public Image image;

    public int getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public List<String> getType() {
        return type;
    }

    public Base getBase() {
        return base;
    }

    public String getSpecies() {
        return species;
    }

    public Evolution getEvolution() {
        return evolution;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getDescription() {
        return description;
    }

    public Image getImage() {
        return image;
    }
}












package dev.nona.pokedex.model.model;

import com.google.gson.annotations.SerializedName;

public class Base{
    public int hp;
    public int attack;
    public int defense;
    @SerializedName("Sp. Attack")
    public int spAttack;
    @SerializedName("Sp. Defense")
    public int spDefense;
    public int speed;

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getSpeed() {
        return speed;
    }

    public int getSpDefense() {
        return spDefense;
    }

    public int getSpAttack() {
        return spAttack;
    }

    public int getDefense() {
        return defense;
    }
}
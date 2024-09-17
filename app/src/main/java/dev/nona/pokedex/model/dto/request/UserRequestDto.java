package dev.nona.pokedex.model.dto.request;

import com.google.gson.annotations.SerializedName;

public class UserRequestDto {
    private String name;
    private String address;
    private int age;
    private String password;

    public UserRequestDto(String name, String address, int age, String password) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.password = password;
    }

}

package dev.nona.pokedex.model.dto.request;

public class SignUpDto {
    private String username;
    private String name;
    private String address;
    private int age;
    private String password;

    public SignUpDto(String username, String name, String address, int age, String password) {
        this.username = username;
        this.name = name;
        this.address = address;
        this.age = age;
        this.password = password;
    }
}

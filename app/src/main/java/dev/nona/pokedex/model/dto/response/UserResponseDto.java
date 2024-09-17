package dev.nona.pokedex.model.dto.response;
import com.google.gson.annotations.SerializedName;

public class UserResponseDto {

    private String username;
    private String name;
    private String address;
    private int age;
    @SerializedName("auth_token")
    private String authToken;

    //status code = response.code()

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public String getAuthToken() {
        return authToken;
    }
}
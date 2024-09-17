package dev.nona.pokedex.api;


import dev.nona.pokedex.model.dto.request.LoginDto;
import dev.nona.pokedex.model.dto.request.SignUpDto;
import dev.nona.pokedex.model.dto.request.UserRequestDto;
import dev.nona.pokedex.model.dto.response.RefreshTokenDto;
import dev.nona.pokedex.model.dto.response.UserResponseDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface UserApi {

    @POST("login")
    Call<RefreshTokenDto> login(@Body LoginDto user);

    @POST("signup")
    Call<SignUpDto> register(@Body SignUpDto user);

    @POST("refresh-token")
    Call<RefreshTokenDto> refreshToken();

    @GET("user/me")
    Call<UserResponseDto> me();

    @PATCH("user/me")
    Call<UserRequestDto> updateMe(@Body UserRequestDto user);

    @POST("logout")
    Call<Void> logout();
}
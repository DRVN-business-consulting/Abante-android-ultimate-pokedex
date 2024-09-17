package dev.nona.pokedex.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;

import dev.nona.pokedex.interceptor.AuthInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private static final String BASE_URL = "http://192.169.8.116:8000/";

    public static String getBaseUrl(){
        return BASE_URL.substring(0, BASE_URL.length() - 1); // Removes the last character
    }


    public static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setStrictness(Strictness.LENIENT)
            .create();

    private static final HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR = new  HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final AuthInterceptor AUTH_INTERCEPTOR = new AuthInterceptor();

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .addInterceptor(AUTH_INTERCEPTOR)
            .addInterceptor(HTTP_LOGGING_INTERCEPTOR)
            .build();

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OK_HTTP_CLIENT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public static UserApi userApi() {
        return RETROFIT.create(UserApi.class);
    }
    public static PokemonApi pokemonApi() {
        return RETROFIT.create(PokemonApi.class);
    }
}

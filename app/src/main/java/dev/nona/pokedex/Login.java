package dev.nona.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dev.nona.pokedex.api.API;
import dev.nona.pokedex.model.dto.request.LoginDto;
import dev.nona.pokedex.model.dto.request.SignUpDto;
import dev.nona.pokedex.model.dto.response.ErrorDto;
import dev.nona.pokedex.model.dto.response.RefreshTokenDto;
import dev.nona.pokedex.model.dto.response.UserResponseDto;
import dev.nona.pokedex.prefs.AppPreferences;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText etUsername, etPassword, etSignUpUsername, etSignUpPassword, etName, etAge, etAddress;
    Button btnLogin, btnSignupToggle, btnSubmitSignUp;;
    LinearLayout signupForm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AppPreferences.initialize(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        etUsername.setText("username");
        etPassword.setText("password");

        btnSignupToggle = findViewById(R.id.btnSignUpToggle);
        signupForm = findViewById(R.id.signupForm);

        etSignUpUsername = findViewById(R.id.etSignUpUsername);
        etSignUpPassword = findViewById(R.id.etSignUpPassword);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etAddress = findViewById(R.id.etAddress);

        btnSubmitSignUp = findViewById(R.id.btnSubmitSignUp);
        btnSubmitSignUp.setOnClickListener(this::btnSubmitSignUp);

//        if (AppPreferences.getInstance().getAccessToken() != null) {
//            me();
//        }


    }


    public void btnLoginClick(View view) {
        API.userApi().login(new LoginDto(etUsername.getText().toString(), etPassword.getText().toString()))
                .enqueue(new Callback<RefreshTokenDto>(){

                    @Override
                    public void onResponse(Call<RefreshTokenDto> call, Response<RefreshTokenDto> response) {
                        if (response.isSuccessful()) {
                            RefreshTokenDto refreshTokenDto = response.body();
                            if (refreshTokenDto != null) {
                                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                AppPreferences.getInstance().setAccessToken(refreshTokenDto.getAccessToken());
                                me();
                            }
                        } else {
                            ResponseBody errorBody = null;
                            try {
                                errorBody = response.errorBody();
                                if (errorBody != null) {
                                    String json = errorBody.string();
                                    ErrorDto errorDto = API.gson.fromJson(json, ErrorDto.class);
                                    Toast.makeText(Login.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
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
                    public void onFailure(Call<RefreshTokenDto> call, Throwable throwable) {
                        Toast.makeText(Login.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void me() {
        API.userApi().me().enqueue(new Callback<UserResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<UserResponseDto> call, @NonNull Response<UserResponseDto> response) {
                if (response.isSuccessful()) {
                    UserResponseDto userDto = response.body();
                    if (userDto != null) {
                        Toast.makeText(Login.this, "Hello, " + userDto.getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                } else {
                    ResponseBody errorBody = null;
                    try {
                        errorBody = response.errorBody();
                        if (errorBody != null) {
                            String json = errorBody.string();
                            ErrorDto errorDto = API.gson.fromJson(json, ErrorDto.class);
                            Toast.makeText(Login.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<UserResponseDto> call, Throwable throwable) {
                Toast.makeText(Login.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btnSignUpClick(View view) {
        if (signupForm.getVisibility() == View.GONE) {
            signupForm.setVisibility(View.VISIBLE);
        }else{
            signupForm.setVisibility(View.GONE);
        }
    }

    public void btnSubmitSignUp(View view) {
        API.userApi().register(
                new SignUpDto(
                        etSignUpUsername.getText().toString(),
                        etName.getText().toString(),
                        etAddress.getText().toString(),
                        Integer.parseInt(etAge.getText().toString()),
                        etSignUpPassword.getText().toString()
        )).enqueue(new Callback<SignUpDto>() {
            @Override
            public void onResponse(Call<SignUpDto> call, Response<SignUpDto> response) {
                if (response.isSuccessful()) {
                    SignUpDto userDto = response.body();
                    if (userDto != null) {
                        Toast.makeText(Login.this, "User Registered", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ResponseBody errorBody = null;
                    try {
                        errorBody = response.errorBody();
                        if (errorBody != null) {
                            String json = errorBody.string();
                            ErrorDto errorDto = API.gson.fromJson(json, ErrorDto.class);
                            Toast.makeText(Login.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<SignUpDto> call, Throwable throwable) {
                Toast.makeText(Login.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
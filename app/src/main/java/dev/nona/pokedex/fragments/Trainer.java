package dev.nona.pokedex.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dev.nona.pokedex.Login;
import dev.nona.pokedex.MainActivity;
import dev.nona.pokedex.R;
import dev.nona.pokedex.api.API;
import dev.nona.pokedex.model.dto.request.UserRequestDto;
import dev.nona.pokedex.model.dto.response.ErrorDto;
import dev.nona.pokedex.model.dto.response.UserResponseDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Trainer extends Fragment {
    EditText etName, etAddress, etAge, etPassword;
    Button btnSave;


    public Trainer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer, container, false);

        // Inflate the layout for this fragment
        etName = view.findViewById(R.id.etName);
        etAddress = view.findViewById(R.id.etAddress);
        etAge = view.findViewById(R.id.etAge);
        etPassword = view.findViewById(R.id.etPassword);

        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this::onSaveClick);

        return view;
    }

    public void onSaveClick(View view) {
        // Get the updated values from the EditTexts
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validate the data (you can add more validations if needed)
        if (name.isEmpty() || address.isEmpty() || age.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        API.userApi().updateMe(
                new UserRequestDto(
                        etName.getText().toString(),
                        etAddress.getText().toString(),
                        Integer.parseInt(etAge.getText().toString()),
                        etPassword.getText().toString()
                )
        ).enqueue(new Callback<UserRequestDto>() {
            @Override
            public void onResponse(Call<UserRequestDto> call, Response<UserRequestDto> response) {
                if (response.isSuccessful()) {
                    UserRequestDto userDto = response.body();
                    if (userDto != null) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
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
                            Toast.makeText(getContext(), errorDto.getDetail(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<UserRequestDto> call, Throwable throwable) {
                Toast.makeText(getContext(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Show a success message
        Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
    }
}
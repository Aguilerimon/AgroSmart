package com.example.agrosmart;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.biometrics.BiometricManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class FingerprintActivity extends AppCompatActivity
{

    String nombre, correo, phone, password, user_id;
    Button btnSwitchVer, btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fingerprint);

        btnSwitchVer = findViewById(R.id.btn_switch_ver);
        btnAccept = findViewById(R.id.btn_accept);

        btnSwitchVer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                goToCodePhone();
            }
        });

        androidx.biometric.BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);
       // BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate())
        {
            case BiometricManager.BIOMETRIC_SUCCESS:
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                goToCodePhone();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                goToCodePhone();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                goToCodePhone();
                break;
        }
        Executor executor = ContextCompat.getMainExecutor(this);

        final BiometricPrompt biometricPrompt = new BiometricPrompt(FingerprintActivity.this, executor, new BiometricPrompt.AuthenticationCallback()
        {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString)
            {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result)
            {
                super.onAuthenticationSucceeded(result);

                Bundle datos = getIntent().getExtras();
                nombre = datos.getString("Name");
                correo = datos.getString("Email");
                phone = datos.getString("PhoneNumber");
                password = datos.getString("Password");
                user_id = datos.getString("User_Id");

                Intent intent = new Intent(FingerprintActivity.this, MainActivity.class);
                intent.putExtra("Name",nombre);
                intent.putExtra("Email", correo);
                intent.putExtra("PhoneNumber", phone);
                intent.putExtra("Password", password);
                intent.putExtra("User_Id", user_id);

                startActivity(intent);
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        final BiometricPrompt.PromptInfo promptInfo = new  BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setDescription(getString(R.string.biometric_success))
                .setNegativeButtonText(getString(R.string.cancel_recover))
                .build();

        btnAccept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                biometricPrompt.authenticate(promptInfo);
            }
        });
        biometricPrompt.authenticate(promptInfo);
    }
    public void goToCodePhone()
    {
        Bundle datos = getIntent().getExtras();
        nombre = datos.getString("Name");
        correo = datos.getString("Email");
        phone = datos.getString("PhoneNumber");
        password = datos.getString("Password");
        user_id = datos.getString("User_Id");

        Intent intent = new Intent(FingerprintActivity.this, LoginCodeActivity.class);
        intent.putExtra("Name",nombre);
        intent.putExtra("Email", correo);
        intent.putExtra("PhoneNumber", phone);
        intent.putExtra("Password", password);
        intent.putExtra("User_Id", user_id);

        startActivity(intent);
        finish();
    }
}

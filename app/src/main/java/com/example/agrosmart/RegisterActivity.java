  package com.example.agrosmart;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity
{
    ProgressDialog progressDialog;
    Button btnRegister, btnAccept, btnCancelRegister, btnCancelCode;
    EditText name, email, phone, password, confirmPass, code;
    CheckBox checkBox;
    CountryCodePicker ccp;
    LinearLayout linearLayoutInformation, linearLayoutCode;
    private String checker = "", phoneNumber = "";
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private String mVerificactionId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        btnRegister = findViewById(R.id.btn_register);
        btnAccept = findViewById(R.id.btn_accept);
        name = findViewById(R.id.edt_name);
        email = findViewById(R.id.edt_email);
        phone = findViewById(R.id.edt_phone);
        password = findViewById(R.id.edt_password);
        confirmPass = findViewById(R.id.edt_confirm_password);
        checkBox = findViewById(R.id.check_terms);
        linearLayoutCode = findViewById(R.id.field_code);
        linearLayoutInformation = findViewById(R.id.field_info);
        code =findViewById(R.id.edt_code);
        btnCancelCode = findViewById(R.id.cancel_register_code);
        btnCancelRegister = findViewById(R.id.cancel_register);

        mAuth = FirebaseAuth.getInstance();
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phone);

        btnCancelCode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnCancelRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                validateRegisterFields();
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
            {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e)
            {
                Toast.makeText(RegisterActivity.this, "Numero de telefono no valido", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
            {
                super.onCodeSent(s, forceResendingToken);

                mVerificactionId = s;
                mResendToken = forceResendingToken;

                linearLayoutInformation.setVisibility(View.GONE);
                checker = "Code Sent";
                linearLayoutCode.setVisibility(View.VISIBLE);

                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Codigo de verificacion  enviado!", Toast.LENGTH_SHORT).show();

            }
        };

        btnAccept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(checker.equals("Code Sent"))
                {
                    String verificationCode = code.getText().toString();
                    if(verificationCode.equals(""))
                    {
                        Toast.makeText(RegisterActivity.this, "Ingresa codigo de verificacion", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        initProgressDialog();
                        showProgressDialog();
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificactionId, verificationCode);
                        signInWithPhoneAuthCredential(credential);
                    }
                }
            }
        });

    }

    private void initProgressDialog()
    {
        this.progressDialog = new ProgressDialog(this);
    }

    private void showProgressDialog()
    {
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(getString(R.string.progress_dialog));
        progressDialog.show();
    }

    public void validateRegisterFields()
    {
        String pass = password.getText().toString();
        String conf_pass = confirmPass.getText().toString();
        if(name.getText().toString().isEmpty())
            name.setError(getString(R.string.empty_name));
        else if(email.getText().toString().isEmpty())
            email.setError(getString(R.string.empty_email));
        else if (!validateEmail(email.getText().toString()))
            email.setError(getString(R.string.invalid_email));
        else if(phone.getText().toString().isEmpty())
            phone.setError(getString(R.string.empty_phone));
        else if(!validatePhone(phone.getText().toString()))
            phone.setError(getString(R.string.invalid_phone));
        else if(password.getText().toString().isEmpty())
            password.setError(getString(R.string.empty_password));
        else if(password.getText().toString().length() < 8)
            password.setError(getString(R.string.min_lenght));
        else if(confirmPass.getText().toString().isEmpty())
            confirmPass.setError(getString(R.string.empty_password));
        else if(!pass.equals(conf_pass))
            confirmPass.setError(getString(R.string.pass_dont_match));
        else if(!checkBox.isChecked())
            checkBox.setError(getString(R.string.check_terms_register));
        else
            sendPhoneCode();
    }

    private boolean validateEmail(String email)
    {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean validatePhone(String phone)
    {
        Pattern pattern = Patterns.PHONE;
        return pattern.matcher(phone).matches();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            registerResponse();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            String e = task.getException().toString();
                            Toast.makeText(RegisterActivity.this, "Error" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed()
    {
        if(linearLayoutCode.getVisibility() == View.VISIBLE)
        {
            Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void sendPhoneCode()
    {
        if(checker.equals("Code Sent"))
        {
            String verificationCode = code.getText().toString();
            if(verificationCode.equals(""))
            {
                Toast.makeText(RegisterActivity.this, "Ingresa codigo de verificacion", Toast.LENGTH_SHORT).show();
            }
            else
            {
                initProgressDialog();
                showProgressDialog();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificactionId, verificationCode);
                signInWithPhoneAuthCredential(credential);
            }
        }
        else
        {
            phoneNumber = ccp.getFullNumberWithPlus();
            if(!phoneNumber.equals(""))
            {
                initProgressDialog();
                showProgressDialog();

                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber(phoneNumber)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(RegisterActivity.this)                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);    // OnVerificationStateChangedCallbacks
            }
            else
            {
                Toast.makeText(RegisterActivity.this, "Ingrese un numero de telefono valido", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void registerResponse()
    {
        String Name = name.getText().toString();
        String PhoneNumber = phone.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        initProgressDialog();
        showProgressDialog();

        Response.Listener<String> responseListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonResponse = new JSONObject(response);

                    boolean successResponse = jsonResponse.getBoolean("success");

                    if(successResponse == true)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, R.string.passed, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        RegisterActivity.this.startActivity(intent);
                        RegisterActivity.this.finish();
                        Toast.makeText(RegisterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                        alert.setMessage(R.string.register_error).setNegativeButton(R.string.retry, null).create().show();
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, R.string.register_error + ": " + e, Toast.LENGTH_LONG).show();
                }
            }

        };

        RegisterRequest registerResponse = new RegisterRequest(Name,PhoneNumber,Email,Password,responseListener);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(registerResponse);
    }
}
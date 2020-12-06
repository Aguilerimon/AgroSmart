package com.example.agrosmart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class LoginCodeActivity extends AppCompatActivity
{
    Button btnEnterPass, btnCancel, btnAccept, btnCancel2, btnSwitchVer;
    ProgressDialog progressDialog;
    CountryCodePicker ccp;
    private String checker = "", phoneNumber = "";
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private String mVerificactionId;
    EditText code, phone;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    LinearLayout linearLayoutPhone, linearLayoutCode;
    String verificationCode;
    String nombre, correo, phone2, password, user_id;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_code);

        btnEnterPass = findViewById(R.id.btn_accept);
        btnCancel = findViewById(R.id.cancel_register_code);
        btnCancel2 = findViewById(R.id.cancel_register_code2);
        phone = findViewById(R.id.edt_phone);
        code =findViewById(R.id.edt_code);
        mAuth = FirebaseAuth.getInstance();
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phone);
        linearLayoutCode = findViewById(R.id.field_code2);
        linearLayoutPhone = findViewById(R.id.field_code);
        btnAccept = findViewById(R.id.btn_accept2);
        btnSwitchVer = findViewById(R.id.btn_switch_ver);

        Bundle datos = getIntent().getExtras();
        nombre = datos.getString("Name");
        correo = datos.getString("Email");
        phone2 = datos.getString("PhoneNumber");
        password = datos.getString("Password");
        user_id = datos.getString("User_Id");

        btnSwitchVer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginCodeActivity.this, FingerprintActivity.class);
                intent.putExtra("Name",nombre);
                intent.putExtra("Email", correo);
                intent.putExtra("PhoneNumber", phone2);
                intent.putExtra("Password", password);
                intent.putExtra("User_Id", user_id);
                startActivity(intent);
                finish();
            }
        });


        btnEnterPass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                validateCodeRecoverFields();
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
                Toast.makeText(LoginCodeActivity.this, R.string.invalid_phone_number, Toast.LENGTH_LONG).show();

                progressDialog.dismiss();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
            {
                super.onCodeSent(s, forceResendingToken);

                mVerificactionId = s;
                mResendToken = forceResendingToken;

                linearLayoutPhone.setVisibility(View.GONE);
                checker = "Code Sent";
                linearLayoutCode.setVisibility(View.VISIBLE);

                progressDialog.dismiss();
                Toast.makeText(LoginCodeActivity.this, R.string.code_sent, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LoginCodeActivity.this, R.string.insert_code, Toast.LENGTH_SHORT).show();
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

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginCodeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnCancel2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                linearLayoutPhone.setVisibility(View.VISIBLE);
                linearLayoutCode.setVisibility(View.GONE);
            }
        });
    }

    public void validateCodeRecoverFields()
    {
        if(phone.getText().toString().isEmpty())
            phone.setError(getString(R.string.empty_phone));
        else if(!validatePhone(phone.getText().toString()))
            phone.setError(getString(R.string.invalid_phone));
        else
            sendPhoneCode();
    }

    private boolean validatePhone(String phone)
    {
        Pattern pattern = Patterns.PHONE;
        return pattern.matcher(phone).matches();
    }

    public void sendPhoneCode()
    {
        if(checker.equals("Code Sent"))
        {
            verificationCode = code.getText().toString();
            if(verificationCode.equals(""))
            {
                Toast.makeText(LoginCodeActivity.this, R.string.insert_code, Toast.LENGTH_SHORT).show();
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
                                .setActivity(LoginCodeActivity.this)                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);    // OnVerificationStateChangedCallbacks
            }
            else
            {
                Toast.makeText(LoginCodeActivity.this, R.string.invalid_phone_number, Toast.LENGTH_SHORT).show();

            }
        }
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
                            Intent intent = new Intent(LoginCodeActivity.this, MainActivity.class);
                            intent.putExtra("Name",nombre);
                            intent.putExtra("Email", correo);
                            intent.putExtra("PhoneNumber", phone2);
                            intent.putExtra("Password", password);
                            intent.putExtra("User_Id", user_id);
                            LoginCodeActivity.this.startActivity(intent);

                        }
                        else
                        {
                            progressDialog.dismiss();
                            String e = task.getException().toString();
                            Toast.makeText(LoginCodeActivity.this, "Error" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

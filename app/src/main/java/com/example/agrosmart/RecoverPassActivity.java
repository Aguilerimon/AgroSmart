package com.example.agrosmart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.agrosmart.NavigationDrawer.Settings.SettingsAccountRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RecoverPassActivity extends AppCompatActivity
{
    Button btnAccept, btnCancel;
    EditText edtPass, edtConfirmPass;
    String phone;
    ProgressDialog progressDialog;
    String phoneWithoutSpace;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_pass);

        btnAccept = findViewById(R.id.btn_accept);
        btnCancel = findViewById(R.id.cancel_register_code);
        edtPass = findViewById(R.id.edt_password);
        edtConfirmPass = findViewById(R.id.edt_confirm_password);

        Bundle datos = getIntent().getExtras();
        phone = datos.getString("Phone");
        phoneWithoutSpace = phone.replace(" ","");

        btnAccept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                validateEditFields();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RecoverPassActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void validateEditFields()
    {
        if(edtPass.getText().toString().isEmpty())
            edtPass.setError(getString(R.string.empty_password));
        else if(edtPass.getText().toString().length() < 8)
            edtPass.setError(getString(R.string.min_lenght));
        else if(edtConfirmPass.getText().toString().isEmpty())
            edtConfirmPass.setError(getString(R.string.empty_password));
        else if(!edtPass.getText().toString().equals(edtConfirmPass.getText().toString()))
            edtConfirmPass.setError(getString(R.string.pass_dont_match));
        else
            editPasswordResponse();
    }

    public void editPasswordResponse()
    {
        String password = edtPass.getText().toString();
        Toast.makeText(RecoverPassActivity.this, "Telefono recibido: " + phoneWithoutSpace + "pwd: " + password, Toast.LENGTH_SHORT).show();


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
                        Intent intent = new Intent(RecoverPassActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(RecoverPassActivity.this, "Contraseña recuperada", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        AlertDialog.Builder alert = new AlertDialog.Builder(RecoverPassActivity.this);
                        alert.setMessage(R.string.register_error).setNegativeButton(R.string.retry, null).create().show();
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(RecoverPassActivity.this, R.string.register_error + ": " + e, Toast.LENGTH_LONG).show();
                }
            }

        };

        RecoverPassRequest recoverPassRequest = new RecoverPassRequest(phoneWithoutSpace, password,responseListener);
        RequestQueue queue = Volley.newRequestQueue(RecoverPassActivity.this);
        queue.add(recoverPassRequest);
    }

    private void initProgressDialog()
    {
        this.progressDialog = new ProgressDialog(RecoverPassActivity.this);
    }

    private void showProgressDialog()
    {
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(getString(R.string.progress_dialog));
        progressDialog.show();
    }

}

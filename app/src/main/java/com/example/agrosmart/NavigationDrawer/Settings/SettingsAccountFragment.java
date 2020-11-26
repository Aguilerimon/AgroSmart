package com.example.agrosmart.NavigationDrawer.Settings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.SettingInjectorService;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.agrosmart.LoginActivity;
import com.example.agrosmart.R;
import com.example.agrosmart.RegisterActivity;
import com.example.agrosmart.RegisterRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneAuthCredential;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class SettingsAccountFragment extends Fragment 
{
    String  name, email, phoneNumber, id;
    Button btnEditInfo, btnCancelEditInfo;
    EditText edtName, edtEmail, edtPhone;
    ImageButton btnEditName, btnEditPhone, btnEditEmail, btnCancelEditName, btnCancelEditPhone, btnCancelEditEmail;
    ProgressDialog progressDialog;


    public SettingsAccountFragment(String id, String nombre, String correo, String phone)
    {
        this.id = id;
        this.name = nombre;
        this.email = correo;
        this.phoneNumber = phone;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) 
    {
        View view = inflater.inflate(R.layout.fragment_settings_account,container,false);

        edtName = view.findViewById(R.id.textViewNombre);
        edtEmail = view.findViewById(R.id.textViewCorreo);
        edtPhone = view.findViewById(R.id.textViewPhone);
        btnEditName = view.findViewById(R.id.btn_edit_name);
        btnEditEmail = view.findViewById(R.id.btn_edit_email);
        btnEditPhone = view.findViewById(R.id.btn_edit_phone);
        btnCancelEditName = view.findViewById(R.id.btn_cancel_edit_name);
        btnCancelEditEmail = view.findViewById(R.id.btn_cancel_edit_email);
        btnCancelEditPhone = view.findViewById(R.id.btn_cancel_edit_phone);
        btnEditInfo = view.findViewById(R.id.btn_edit_info);
        btnCancelEditInfo = view.findViewById(R.id.btn_cancel_edit_info);

        btnEditName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnCancelEditName.setVisibility(View.VISIBLE);
                btnEditName.setVisibility(View.GONE);
                edtName.setEnabled(true);
                showEditButtons();
            }
        });

        btnCancelEditName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnCancelEditName.setVisibility(View.GONE);
                btnEditName.setVisibility(View.VISIBLE);
                edtName.setEnabled(false);
                edtName.setText(name);
                showEditButtons();
            }
        });

        btnEditEmail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnCancelEditEmail.setVisibility(View.VISIBLE);
                btnEditEmail.setVisibility(View.GONE);
                edtEmail.setEnabled(true);
                showEditButtons();

            }
        });

        btnCancelEditEmail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnCancelEditEmail.setVisibility(View.GONE);
                btnEditEmail.setVisibility(View.VISIBLE);
                edtEmail.setEnabled(false);
                edtEmail.setText(email);
                showEditButtons();
            }
        });
        btnEditPhone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnCancelEditPhone.setVisibility(View.VISIBLE);
                btnEditPhone.setVisibility(View.GONE);
                edtPhone.setEnabled(true);
                showEditButtons();
            }
        });

        btnCancelEditPhone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnCancelEditPhone.setVisibility(View.GONE);
                btnEditPhone.setVisibility(View.VISIBLE);
                edtPhone.setEnabled(false);
                edtPhone.setText(phoneNumber);
                showEditButtons();
            }
        });

        btnCancelEditInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                   initialInfo();
            }
        });

        btnEditInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                validateEditFields();
            }
        });

        edtName.setText(name);
        edtEmail.setText(email);
        edtPhone.setText(phoneNumber);

        return view;
    }

    public void showEditButtons()
    {
        if(btnCancelEditName.getVisibility() == View.VISIBLE || btnCancelEditEmail.getVisibility() == View.VISIBLE || btnCancelEditPhone.getVisibility() == View.VISIBLE)
        {
            btnCancelEditInfo.setVisibility(View.VISIBLE);
            btnEditInfo.setVisibility(View.VISIBLE);
        }
        else
        {
            btnCancelEditInfo.setVisibility(View.GONE);
            btnEditInfo.setVisibility(View.GONE);
        }
    }

    public void editAccountResponse()
    {
        String Name = edtName.getText().toString();
        String PhoneNumber = edtPhone.getText().toString();
        String Email = edtEmail.getText().toString();
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
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        progressDialog.dismiss();
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setMessage(R.string.register_error).setNegativeButton(R.string.retry, null).create().show();
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), R.string.register_error + ": " + e, Toast.LENGTH_LONG).show();
                }
            }

        };

        SettingsAccountRequest settingsAccountResponse = new SettingsAccountRequest(id, Name,PhoneNumber,Email,responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(settingsAccountResponse);
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

    public void validateEditFields()
    {
        if(edtName.getText().toString().isEmpty())
            edtName.setError(getString(R.string.empty_name));
        else if(edtEmail.getText().toString().isEmpty())
            edtEmail.setError(getString(R.string.empty_email));
        else if (!validateEmail(edtEmail.getText().toString()))
            edtEmail.setError(getString(R.string.invalid_email));
        else if(edtPhone.getText().toString().isEmpty())
            edtPhone.setError(getString(R.string.empty_phone));
        else if(!validatePhone(edtPhone.getText().toString()))
            edtPhone.setError(getString(R.string.invalid_phone));
        else
            editAccountResponse();
    }

    private void initProgressDialog()
    {
        this.progressDialog = new ProgressDialog(getContext());
    }

    private void showProgressDialog()
    {
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(getString(R.string.progress_dialog));
        progressDialog.show();
    }

    private void initialInfo()
    {
        btnCancelEditPhone.setVisibility(View.GONE);
        btnEditPhone.setVisibility(View.VISIBLE);
        btnCancelEditEmail.setVisibility(View.GONE);
        btnEditEmail.setVisibility(View.VISIBLE);
        btnCancelEditName.setVisibility(View.GONE);
        btnEditName.setVisibility(View.VISIBLE);
        btnCancelEditInfo.setVisibility(View.GONE);
        btnEditInfo.setVisibility(View.GONE);
    }
}
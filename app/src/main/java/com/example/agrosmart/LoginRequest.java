package com.example.agrosmart;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest
{
    private static final String loginRequest_URL = "http://agrosmartamm.000webhostapp.com/agrosmart/login_register/Login.php";
    private Map<String, String> params;

    public LoginRequest(String Email, String Password, Response.Listener<String> listener)
    {
        super(Method.POST, loginRequest_URL, listener, null);
        params = new HashMap<>();
        params.put("Email", Email + "");
        params.put("Password", Password + "");
    }

    @Override
    public Map<String, String> getParams()
    {
        return params;
    }
}

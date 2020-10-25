package com.example.agrosmart;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class RegisterRequest extends StringRequest
{

    private static final String registerRequest_URL = "http://192.168.100.17/AgroSmartPhp/login_register/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String Name, String PhoneNumber, String Email, String Password, Response.Listener<String> listener)
    {
        super(Method.POST, registerRequest_URL, listener, null);
        params = new HashMap<>();
        params.put("Name", Name + "");
        params.put("PhoneNumber", PhoneNumber + "");
        params.put("Email", Email + "");
        params.put("Password", Password + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

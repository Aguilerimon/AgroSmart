package com.example.agrosmart;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class RecoverPassRequest extends StringRequest
{

    private static final String recoverPasswordRequest_URL = "http://agrosmartamm.000webhostapp.com/agrosmart/UpdatePass.php";
    private Map<String, String> params;

    public RecoverPassRequest(String PhoneNumber, String Pass, Response.Listener<String> listener)
    {
        super(Method.POST, recoverPasswordRequest_URL, listener, null);
        params = new HashMap<>();
        params.put("PhoneNumber",   PhoneNumber + "");
        params.put("Password",   Pass + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

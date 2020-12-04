package com.example.agrosmart.NavigationDrawer.Settings;

import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class SettingsAccountRequest extends StringRequest
{

    private static final String settingsAccountRequest_URL = "http://agrosmartamm.000webhostapp.com/agrosmart/UpdateInfo.php";
    private Map<String, String> params;

    public SettingsAccountRequest(String Id, String Name, String PhoneNumber, String Email, Response.Listener<String> listener)
    {
        super(Method.POST, settingsAccountRequest_URL, listener, null);
        params = new HashMap<>();
        params.put("User_Id", Id + "");
        params.put("Name", Name + "");
        params.put("PhoneNumber", PhoneNumber + "");
        params.put("Email", Email + "");

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

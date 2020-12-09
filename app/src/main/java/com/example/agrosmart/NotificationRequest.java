package com.example.agrosmart;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class NotificationRequest extends StringRequest
{

    private static final String registerRequest_URL = "http://agrosmartamm.000webhostapp.com/agrosmart/notification.php";
    private Map<String, String> params;

    public NotificationRequest(String notificationText, String user_id, Response.Listener<String> listener)
    {
        super(Method.POST, registerRequest_URL, listener, null);
        params = new HashMap<>();
        params.put("notification", notificationText + "");
        params.put("user_id", user_id + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

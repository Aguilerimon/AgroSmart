package com.example.agrosmart.NavigationDrawer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.agrosmart.MainActivity;
import com.example.agrosmart.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeFragment extends Fragment
{
    TextView aguaHora1, aguaValor1, aguaHora2, aguaValor2, aguaHora3, aguaValor3;
    TextView tierraHora1, tierraValor1, tierraHora2, tierraValor2, tierraHora3, tierraValor3;
    public static Bundle datos = new Bundle();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        aguaHora1 = root.findViewById(R.id.Tb_Agua_Hora1);
        aguaHora2 = root.findViewById(R.id.Tb_Agua_Hora2);
        aguaHora3 = root.findViewById(R.id.Tb_Agua_Hora3);

        aguaValor1 = root.findViewById(R.id.Tb_Agua_Valor1);
        aguaValor2 = root.findViewById(R.id.Tb_Agua_Valor2);
        aguaValor3 = root.findViewById(R.id.Tb_Agua_Valor3);

        tierraHora1 = root.findViewById(R.id.Tb_Tierra_Hora1);
        tierraHora2 = root.findViewById(R.id.Tb_Tierra_Hora2);
        tierraHora3 = root.findViewById(R.id.Tb_Tierra_Hora3);

        tierraValor1 = root.findViewById(R.id.Tb_Tierra_Valor1);
        tierraValor2 = root.findViewById(R.id.Tb_Tierra_Valor2);
        tierraValor3 = root.findViewById(R.id.Tb_Tierra_Valor3);

        aguaHora1.setText(datos.getString("dateAgua0"));
        aguaValor1.setText(datos.getString("humidity0"));
        aguaHora2.setText(datos.getString("dateAgua1"));
        aguaValor2.setText(datos.getString("humidity1"));
        aguaHora3.setText(datos.getString("dateAgua2"));
        aguaValor3.setText(datos.getString("humidity2"));

        tierraHora1.setText(datos.getString("dateTierra0"));
        tierraValor1.setText(datos.getString("ground_humidity0"));
        tierraHora2.setText(datos.getString("dateTierra1"));
        tierraValor2.setText(datos.getString("ground_humidity1"));
        tierraHora3.setText(datos.getString("dateTierra2"));
        tierraValor3.setText(datos.getString("ground_humidity2"));



        return root;
    }


}
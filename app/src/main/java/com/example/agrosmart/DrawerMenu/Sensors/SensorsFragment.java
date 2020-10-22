package com.example.agrosmart.DrawerMenu.Sensors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.agrosmart.R;

public class SensorsFragment extends Fragment {

    private SensorsViewModel sensorsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sensorsViewModel =
                new ViewModelProvider(this).get(SensorsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sensors, container, false);
        final TextView textView = root.findViewById(R.id.text_sensors);
        sensorsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
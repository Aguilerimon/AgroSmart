package com.example.agrosmart.DrawerMenu.Specs;

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

public class SpecsFragment extends Fragment {

    private SpecsViewModel specsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        specsViewModel =
                new ViewModelProvider(this).get(SpecsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_specs, container, false);
        final TextView textView = root.findViewById(R.id.text_specs);
        specsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
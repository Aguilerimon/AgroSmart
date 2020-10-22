package com.example.agrosmart.DrawerMenu.Specs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SpecsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SpecsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Specs fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
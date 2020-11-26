package com.example.agrosmart.NavigationDrawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.agrosmart.R;

public class ContactFragment extends Fragment
{
    private String email;
    Button btnSend;
    EditText subject, messaje;

    public ContactFragment(String email)
    {
        this.email = email;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_contact, container, false);

        subject = root.findViewById(R.id.edt_subject);
        messaje = root.findViewById(R.id.edt_message);
        btnSend = root.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String mEmail = email.trim();
                String mSubject = subject.getText().toString().trim();
                String mMessaje = messaje.getText().toString().trim();

                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { mEmail });
                intent.putExtra(Intent.EXTRA_SUBJECT, mSubject);
                intent.putExtra(Intent.EXTRA_TEXT, mMessaje);

                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent, getString(R.string.choose_mailer)));
            }
        });

        return root;
    }
}
package com.example.agrosmart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import static com.example.agrosmart.MainActivity.NOTIFICATION_ID;

public class NotificationsFragment extends Fragment
{
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
        notificationManagerCompat.cancel(NOTIFICATION_ID);
        return root;
    }
}
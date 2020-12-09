package com.example.agrosmart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import static com.example.agrosmart.MainActivity.NOTIFICATION_ID;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment
{

    ListView lista;
    ArrayAdapter<String> adapter;
    Bundle bundle = FingerprintActivity.bundle;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
        notificationManagerCompat.cancel(NOTIFICATION_ID);

        lista = root.findViewById(R.id.list_Notifications);
        ArrayList<String> stringArray = new ArrayList<String>();

        for( int i=0; i<10; i++ ){

            String notifKey = "notif" + i;
            String dateKey = "dateNotif" + i;

            if(bundle.containsKey(notifKey) && bundle.containsKey(dateKey)){
                stringArray.add(bundle.getString(notifKey) + ":  " + bundle.getString(dateKey));
            }

        }

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item_1, stringArray);
        lista.setAdapter(adapter);

        return root;
    }
}
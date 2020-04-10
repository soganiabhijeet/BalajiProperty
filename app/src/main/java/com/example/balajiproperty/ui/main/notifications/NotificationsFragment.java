package com.example.balajiproperty.ui.main.notifications;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balajiproperty.MainActivity;
import com.example.balajiproperty.R;
import com.example.balajiproperty.adapter.SMSAdapter;
import com.example.balajiproperty.model.SMSModel;
import com.example.balajiproperty.sqlite.helper.BalajiPropertyDbHelper;
import com.example.balajiproperty.sqlite.model.MessageSentDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private RecyclerView recyclerView;
    private SMSAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fab;
    private int SMS_CODE = 1;
    SmsManager smsManager = SmsManager.getDefault();
    BalajiPropertyDbHelper balajiPropertyDbHelper;
    MessageSentDao messageSentDao;
    CopyOnWriteArrayList<SMSModel> smsModels = new CopyOnWriteArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        balajiPropertyDbHelper = new BalajiPropertyDbHelper(getContext());
        messageSentDao = new MessageSentDao(balajiPropertyDbHelper);
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);
        fab = root.findViewById(R.id.fab);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        for (int i = 0; i < 10; i++) {
            smsModels.add(new SMSModel("sms text " + i + " Like any other social media site Facebook has length requirements when it comes to writing on the wall, providing status, messaging and commenting.", UUID.randomUUID().variant(), true));
        }

        mAdapter = new SMSAdapter(getContext(), smsModels, new NavigatorListener());
        recyclerView.setAdapter(mAdapter);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (permissionAlreadyGranted()) {
                    Integer toRemoveCounter = 0;
                    //Toast.makeText(getContext(), "Permission is already granted!", Toast.LENGTH_SHORT).show();
                    for (SMSModel smsModel : mAdapter.getSMSList()) {

                        if (smsModel.getToSend()) {
                            smsManager.sendTextMessage("9910291926", null, smsModels.get(0).getNavigatorName(), null, null);
                            messageSentDao.addSMSMessage();
                            List<Integer> smsread = messageSentDao.readSMSMessage();
                            //Snackbar.make(view, "SMS to 9910291926 sent " + smsread.size(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            mAdapter.getSMSList().remove(smsModel);
                            mAdapter.notifyItemRemoved(toRemoveCounter);
                            mAdapter.notifyItemRangeChanged(toRemoveCounter, smsModels.size());
                        } else {
                            //IncreaseToRemoveCounter by 1
                            toRemoveCounter++;
                        }
                    }

                    return;
                }

                requestPermission();

            }
        });
        return root;
    }

    public class NavigatorListener implements SMSAdapter.NavigatorAdapterListener {

        @Override
        public void onNavigatorSelected(SMSModel navigator, View view) {

        }
    }


    private boolean permissionAlreadyGranted() {

        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS);

        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        return false;
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.SEND_SMS)) {

        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, SMS_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == SMS_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission granted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission is denied!", Toast.LENGTH_SHORT).show();
                boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS);
                if (!showRationale) {
                    openSettingsDialog();
                }
            }
        }
    }

    private void openSettingsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Required Permissions");
        builder.setMessage("This app require permission to use awesome feature. Grant them in app settings.");
        builder.setPositiveButton("Take Me To SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
}





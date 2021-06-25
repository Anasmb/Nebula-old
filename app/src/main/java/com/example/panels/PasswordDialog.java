package com.example.panels;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PasswordDialog extends AppCompatDialogFragment {

    private EditText editTextPassword;
    private Button connectBtn;
    private TextView ssidText ;

    private String ssidName;
    private PasswordDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_password,null);


        builder.setView(view).setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String password = editTextPassword.getText().toString();
                listener.getInformation(password);
            }
        });

        editTextPassword = view.findViewById(R.id.editPassword);

        return builder.create();
    }

    public void setSsidName(String ssid){
        ssidName = ssid;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (PasswordDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement PasswordDialogListener");
        }
    }

    public interface PasswordDialogListener{
        void getInformation(String password);
    }
}

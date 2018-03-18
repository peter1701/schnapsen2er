package com.example.peter.cardanimationtest.gui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.peter.cardanimationtest.R;
import com.example.peter.cardanimationtest.service.Schnapsen2erService;

/**
 * Created by Peter on 24.02.2018.
 */

public class GameEndDialog extends DialogFragment {



    private String endMessage=null;
    public void setEndMessage(String endMessage){
        this.endMessage=endMessage;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(endMessage)
                .setPositiveButton(R.string.restart, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Schnapsen2erService.getInstance().setUp();
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();

    }
}

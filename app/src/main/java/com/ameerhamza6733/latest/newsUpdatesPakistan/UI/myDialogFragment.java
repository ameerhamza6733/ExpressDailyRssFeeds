package com.ameerhamza6733.latest.newsUpdatesPakistan.UI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by AmeerHamza on 9/30/2016.
 */

public class myDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("ہم اسے کس طرح بہتر بنا سکتے ہیں")
                .setPositiveButton("رائے بھیجیں", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try{
                            Intent Email = new Intent(Intent.ACTION_SEND);
                            Email.setType("text/email");
                            Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "ameerhamza6733@gmail.com" });
                            Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                            Email.putExtra(Intent.EXTRA_TEXT, "Dear ...," + "");
                            startActivity(Intent.createChooser(Email, "Send Feedback:"));

                        }catch (Exception e)
                        {

                        }
                    }
                })
                .setNegativeButton("بند کریں", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        getActivity().finish();

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
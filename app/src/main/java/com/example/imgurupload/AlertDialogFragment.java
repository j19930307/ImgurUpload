package com.example.imgurupload;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.imgurupload.api.ImgurApiService;
import com.example.imgurupload.api.Response;
import com.example.imgurupload.api.RetrofitService;
import com.example.imgurupload.image.Image;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Jason on 2018/3/1.
 */

public class AlertDialogFragment extends DialogFragment {


    private String deletehash;
    private String link;

    private AlertDialogListener listener;

    public interface AlertDialogListener {
        void onDialogCopyClick();
        void onDialogDeleteClick();
        void onDialogShareClick();
    }

    public static AlertDialogFragment newInstance() {
        AlertDialogFragment fragment = new AlertDialogFragment();
        /*String deletehash = image.getDeletehash();
        String link = image.getLink();
        Bundle bundle = new Bundle();
        bundle.putString(DELETEHASH, deletehash);
        bundle.putString(LINK, link);
        fragment.setArguments(bundle);*/
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AlertDialogListener) context;
        }  catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }

        Log.d("onCreate", "onCreate");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = (AlertDialogListener) getTargetFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //deletehash = getArguments().getString(DELETEHASH);
        //link = getArguments().getString(LINK);


        return new AlertDialog.Builder(getActivity())
                .setItems(R.array.photo_long_press_options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                listener.onDialogCopyClick();
                                break;
                            case 1:
                                listener.onDialogDeleteClick();
                                break;
                            case 2:
                                listener.onDialogShareClick();
                                break;
                        }
                    }
                })
                .create();
    }
}

package com.example.imgurupload.fragment;

import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.imgurupload.R;

public class BaseFragment extends Fragment {

    private static final String LINK = "link";

    protected void copyLink(String link) {
        ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(LINK, link);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getActivity(), R.string.copy_to_clipboard, Toast.LENGTH_SHORT).show();
    }

    protected void shareLink(String link) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, link);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getString(R.string.share)));
    }
}

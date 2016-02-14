package com.gdx.wallpaper.setting.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.gdx.wallpaper.R;

public class WallpaperImageEditHelpDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_wallpaper_image_edit_help_title)
                .setMessage(R.string.dialog_wallpaper_image_edit_help_message)
                .setPositiveButton(R.string.ok, null);

        return builder.create();
    }
}

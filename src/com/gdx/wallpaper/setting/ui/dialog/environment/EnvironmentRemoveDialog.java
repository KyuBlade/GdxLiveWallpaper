package com.gdx.wallpaper.setting.ui.dialog.environment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.environment.EnvironmentRemoveEvent;

public class EnvironmentRemoveDialog extends DialogFragment {

    public static final String TAG = "EnvironmentRemoveDialog";

    private static final String ENVIRONMENT_ID = "EnvironmentId";

    public EnvironmentRemoveDialog() {
    }

    public static EnvironmentRemoveDialog newInstance(long environmentId) {
        Bundle args = new Bundle();
        args.putLong(ENVIRONMENT_ID, environmentId);

        EnvironmentRemoveDialog fragment = new EnvironmentRemoveDialog();
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        final long environmentId = args.getLong(ENVIRONMENT_ID, -1);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_remove_title)
                .setMessage(R.string.dialog_remove_environment_message)
                .setPositiveButton(R.string.ok, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BusProvider.getInstance().post(new EnvironmentRemoveEvent(environmentId));
                    }
                }).setNegativeButton(R.string.cancel, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}
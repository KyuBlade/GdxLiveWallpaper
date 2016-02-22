package com.gdx.wallpaper.setting.ui.dialog.transition;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionRemovedEvent;

public class TransitionRemoveDialog extends DialogFragment {

    public static final String TAG = "TransitionRemoveDialog";

    public static final String TRANSITION_ID = "TransitionId";

    public TransitionRemoveDialog() {
    }

    public static TransitionRemoveDialog newInstance(long transitionId) {
        Bundle args = new Bundle();
        args.putLong(TRANSITION_ID, transitionId);

        TransitionRemoveDialog fragment = new TransitionRemoveDialog();
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        final long transitionId = args.getLong(TRANSITION_ID, -1);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_remove_title)
                .setMessage(R.string.dialog_remove_transition_message)
                .setPositiveButton(R.string.ok, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BusProvider.getInstance().post(new TransitionRemovedEvent(transitionId));
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
package com.gdx.wallpaper.setting.ui.dialog.transition;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionCreatedEvent;
import com.gdx.wallpaper.transition.TransitionType;

public class TransitionTypeChoiceDialog extends DialogFragment {

    public static final String TAG = "TransitionTypeChoiceDialog";

    private TransitionType[] items;
    private int selectedIndex;

    public TransitionTypeChoiceDialog() {
        items = TransitionType.values();
    }

    public static TransitionTypeChoiceDialog newInstance() {
        return new TransitionTypeChoiceDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] names = new String[items.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = getActivity().getString(items[i].getNameRes());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_edit_transition_title)
                .setSingleChoiceItems(names, selectedIndex, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedIndex = which;
                    }
                }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                TransitionType type = items[selectedIndex];
                dialog.dismiss();
                BusProvider.getInstance().post(new TransitionCreatedEvent(type));
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        return builder.create();
    }
}
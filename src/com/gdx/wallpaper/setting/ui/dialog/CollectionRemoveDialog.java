package com.gdx.wallpaper.setting.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionRemovedEvent;

public class CollectionRemoveDialog extends DialogFragment {

    public static final String TAG = "CollectionRemoveDialog";

    private static final String COLLECTION_ID = "CollectionId";

    public CollectionRemoveDialog() {
    }

    public static CollectionRemoveDialog newInstance(long collectionId) {
        Bundle bundle = new Bundle();
        bundle.putLong(COLLECTION_ID, collectionId);

        CollectionRemoveDialog fragment = new CollectionRemoveDialog();
        fragment.setArguments(bundle);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        final long collectionId = args.getLong(COLLECTION_ID, -1);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_remove_title)
                .setMessage(R.string.dialog_remove_collection_message)
                .setPositiveButton(R.string.ok, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BusProvider.getInstance().post(new CollectionRemovedEvent(collectionId));
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
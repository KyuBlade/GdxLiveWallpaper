package com.gdx.wallpaper.setting.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.collection.CollectionManager;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.database.operation.CollectionNameUpdateOperation;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionChangedEvent;

public class CollectionNameEditDialog extends TextInputDialog {

    public static final String TAG = "CollectionNameEditDialog";

    private static final String COLLECTION_ID = "CollectionId";

    private Collection collection;

    public CollectionNameEditDialog() {
        super(R.string.dialog_collection_edit_name_title,
              R.string.dialog_collection_edit_name_message);
    }

    public static CollectionNameEditDialog newInstance(long collectionId, String defaultName) {
        Bundle args = new Bundle();
        args.putLong(COLLECTION_ID, collectionId);
        args.putString(DEFAULT_TEXT, defaultName);

        CollectionNameEditDialog fragment = new CollectionNameEditDialog();
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        long collectionId = args.getLong(COLLECTION_ID);
        collection = CollectionManager.getInstance().get(collectionId);

        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onPositive(String text) {
        collection.setName(text);
        BusProvider.getInstance()
                .post(new CollectionChangedEvent(collection, new CollectionNameUpdateOperation()));
    }
}
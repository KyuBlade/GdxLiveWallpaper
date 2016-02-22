package com.gdx.wallpaper.setting.ui.dialog.transition;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.database.operation.transition.TransitionNameUpdateOperation;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionChangedEvent;
import com.gdx.wallpaper.setting.ui.dialog.TextInputDialog;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.TransitionManager;

public class TransitionNameEditDialog extends TextInputDialog {

    public static final String TAG = "TransitionNameEditDialog";

    private static final String TRANSITION_ID = "TransitionId";

    private Transition transition;

    public TransitionNameEditDialog() {
        super(R.string.dialog_transition_edit_name_title,
              R.string.dialog_transition_edit_name_message);
    }

    public static TransitionNameEditDialog newInstance(long transitionId, String defaultName) {
        Bundle args = new Bundle();
        args.putLong(TRANSITION_ID, transitionId);
        args.putString(DEFAULT_TEXT, defaultName);

        TransitionNameEditDialog fragment = new TransitionNameEditDialog();
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        long transitionId = args.getLong(TRANSITION_ID);
        transition = TransitionManager.getInstance().get(transitionId);

        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onPositive(String text) {
        transition.setName(text);
        BusProvider.getInstance()
                .post(new TransitionChangedEvent(transition.getId(),
                                                 new TransitionNameUpdateOperation()));
    }
}
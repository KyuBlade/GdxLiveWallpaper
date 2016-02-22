package com.gdx.wallpaper.setting.ui.dialog.environment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.environment.EnvironmentManager;
import com.gdx.wallpaper.setting.database.operation.environment.EnvironmentNameUpdateOperation;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.environment.EnvironmentChangedEvent;
import com.gdx.wallpaper.setting.ui.dialog.TextInputDialog;

public class EnvironmentNameEditDialog extends TextInputDialog {

    public static final String TAG = "EnvironmentNameEditDialog";

    private static final String ENVIRONMENT_ID = "EnvironmentId";

    private Environment environment;

    public EnvironmentNameEditDialog() {
        super(R.string.dialog_environment_edit_name_title,
              R.string.dialog_environment_edit_name_message);
    }

    public static EnvironmentNameEditDialog newInstance(long environmentId, String defaultName) {
        Bundle args = new Bundle();
        args.putLong(ENVIRONMENT_ID, environmentId);
        args.putString(DEFAULT_TEXT, defaultName);

        EnvironmentNameEditDialog fragment = new EnvironmentNameEditDialog();
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        long environmentId = args.getLong(ENVIRONMENT_ID);
        environment = EnvironmentManager.getInstance().get(environmentId);

        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onPositive(String text) {
        environment.setName(text);
        BusProvider.getInstance()
                .post(new EnvironmentChangedEvent(environment.getId(),
                                                  new EnvironmentNameUpdateOperation()));
    }
}
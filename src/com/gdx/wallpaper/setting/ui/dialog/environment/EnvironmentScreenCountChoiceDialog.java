package com.gdx.wallpaper.setting.ui.dialog.environment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.environment.EnvironmentManager;
import com.gdx.wallpaper.setting.database.operation.environment.EnvironmentScreenCountUpdateOperation;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.environment.EnvironmentChangedEvent;

public class EnvironmentScreenCountChoiceDialog extends DialogFragment {

    public static final String TAG = "EnvironmentScreenCountChoiceDialog";

    public static final String ENVIRONMENT_ID = "EnvironmentId";
    public static final String DEFAULT_VALUE = "DefaultValue";

    private static final int MAX_SCREEN_COUNT = 10;

    private int[] items;
    private int selectedIndex;

    public EnvironmentScreenCountChoiceDialog() {
        items = new int[MAX_SCREEN_COUNT];
        for (int i = 0; i < MAX_SCREEN_COUNT; i++) {
            items[i] = i + 1;
        }
    }

    public static EnvironmentScreenCountChoiceDialog newInstance(long environmentId,
                                                                 int defaultValue) {
        EnvironmentScreenCountChoiceDialog fragment = new EnvironmentScreenCountChoiceDialog();
        Bundle bundle = new Bundle();
        bundle.putLong(ENVIRONMENT_ID, environmentId);
        bundle.putInt(DEFAULT_VALUE, defaultValue - 1);
        fragment.setArguments(bundle);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] names = new String[items.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = String.valueOf(items[i]);
        }

        Bundle args = getArguments();
        final long environmentId = args.getLong(ENVIRONMENT_ID);
        selectedIndex = args.getInt(DEFAULT_VALUE);
        if (selectedIndex < 0 || selectedIndex > MAX_SCREEN_COUNT - 1) {
            selectedIndex = 0;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_edit_environment_screen_count_title)
                .setSingleChoiceItems(names, selectedIndex, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedIndex = which;
                    }
                }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                int screenCount = items[selectedIndex];
                dialog.dismiss();

                Environment environment = EnvironmentManager.getInstance().get(environmentId);
                environment.setScreenCount(screenCount);
                BusProvider.getInstance().post(new EnvironmentChangedEvent(environmentId,
                                                                           new EnvironmentScreenCountUpdateOperation()));

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
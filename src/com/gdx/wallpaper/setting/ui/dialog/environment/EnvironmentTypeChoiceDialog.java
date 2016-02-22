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
import com.gdx.wallpaper.environment.EnvironmentType;
import com.gdx.wallpaper.setting.database.operation.environment.EnvironmentTypeUpdateOperation;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.environment.EnvironmentChangedEvent;

public class EnvironmentTypeChoiceDialog extends DialogFragment {

    public static final String TAG = "EnvironmentTypeChoiceDialog";

    public static final String ENVIRONMENT_ID = "EnvironmentId";
    public static final String DEFAULT_VALUE = "DefaultValue";

    private EnvironmentType[] items;
    private int selectedIndex;

    public EnvironmentTypeChoiceDialog() {
        items = EnvironmentType.values();
    }

    public static EnvironmentTypeChoiceDialog newInstance(long environmentId,
                                                          EnvironmentType defaultValue) {
        EnvironmentTypeChoiceDialog fragment = new EnvironmentTypeChoiceDialog();
        Bundle bundle = new Bundle();
        bundle.putLong(ENVIRONMENT_ID, environmentId);
        bundle.putInt(DEFAULT_VALUE, defaultValue.ordinal());
        fragment.setArguments(bundle);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] names = new String[items.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = getActivity().getString(items[i].getNameRes());
        }

        Bundle args = getArguments();
        final long environmentId = args.getLong(ENVIRONMENT_ID);
        selectedIndex = args.getInt(DEFAULT_VALUE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_edit_environment_type_title)
                .setSingleChoiceItems(names, selectedIndex, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedIndex = which;
                    }
                }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                EnvironmentType type = items[selectedIndex];
                dialog.dismiss();

                Environment environment = EnvironmentManager.getInstance().get(environmentId);
                environment.setType(type);
                BusProvider.getInstance().post(new EnvironmentChangedEvent(environmentId,
                                                                           new EnvironmentTypeUpdateOperation()));

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
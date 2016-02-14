package com.gdx.wallpaper.setting.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.EditText;

import com.gdx.wallpaper.R;

public abstract class TextInputDialog extends DialogFragment {

    private int title;
    private int message;
    private EditText editText;

    protected static final String DEFAULT_TEXT = "DefaultText";

    public TextInputDialog(int title, int message) {
        this.title = title;
        this.message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle _args = getArguments();
        String _defaultName = _args.getString(DEFAULT_TEXT);

        editText = new EditText(getActivity());
        editText.setPadding(10, 5, 10, 5);
        editText.setText(_defaultName);
        editText.setSingleLine();
        if (_defaultName != null) {
            editText.setSelection(_defaultName.length());
        }

        AlertDialog.Builder _builder = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message).setView(editText)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onPositive(getText());
                    }
                });

        Dialog _dialog = _builder.create();
        _dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return _dialog;
    }

    public String getText() {
        return editText.getText().toString();
    }

    public abstract void onPositive(String text);
}

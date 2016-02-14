package com.gdx.wallpaper.util;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

public class PermissionUtils {

    public static final int READ_EXTERNAL_STORAGE_REQUEST = 0;

    public static boolean invokeReadExternalStoragePermission(Fragment fragment) {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if (ContextCompat
                .checkSelfPermission(fragment.getActivity(), permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (fragment.shouldShowRequestPermissionRationale(permission)) {
            }

            fragment.requestPermissions(new String[] { permission },
                                        READ_EXTERNAL_STORAGE_REQUEST);
            return false;
        }

        return true;
    }
}

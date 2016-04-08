package com.gdx.wallpaper.setting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

public abstract class AbstractPreviewFragment<T extends AbstractShaderPreviewApplication>
        extends AndroidFragmentApplication
        implements AndroidFragmentApplication.Callbacks {

    public static final String TAG = "AbstractPreviewFragment";

    protected T app;
    private ProgressBar radialProgress;

    private OnLoadedListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = initApplication();
        app.setRadialProgress(radialProgress);
        if (listener != null) {
            listener.onLoaded(app);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return initializeForView(app);
    }

    @Override
    public void exit() {
    }

    protected abstract T initApplication();

    protected void setRadialProgress(ProgressBar radialProgress) {
        this.radialProgress = radialProgress;
    }

    public void setListener(OnLoadedListener listener) {
        this.listener = listener;
    }

    public interface OnLoadedListener {

        void onLoaded(AbstractShaderPreviewApplication app);
    }
}
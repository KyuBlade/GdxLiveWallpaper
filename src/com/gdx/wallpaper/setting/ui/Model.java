package com.gdx.wallpaper.setting.ui;

import com.badlogic.gdx.utils.Array;
import com.gdx.wallpaper.setting.fragment.AbstractShaderPreviewApplication;
import com.gdx.wallpaper.setting.fragment.adapter.control.EditControl;

public class Model extends Array<EditControl> {

    protected AbstractShaderPreviewApplication app;

    public Model(AbstractShaderPreviewApplication app) {
        this.app = app;
    }
}
package com.gdx.wallpaper.setting.ui;

import com.badlogic.gdx.utils.Array;

public class ButtonGroup {

    private Array<RadioButton> buttons;

    public ButtonGroup() {
        buttons = new Array<RadioButton>();
    }

    public void add(RadioButton button) {
        buttons.add(button);
    }
}

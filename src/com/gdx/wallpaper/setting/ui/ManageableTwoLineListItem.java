package com.gdx.wallpaper.setting.ui;

import com.gdx.wallpaper.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class ManageableTwoLineListItem extends TwoLineListItem {

    private ImageButton manageButton;

    public ManageableTwoLineListItem(Context context) {
	this(context, null, 0);
    }

    public ManageableTwoLineListItem(Context context, AttributeSet attrs) {
	this(context, attrs, 0);
    }

    public ManageableTwoLineListItem(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle, R.layout.two_line_manage_list_item);
    }

    @Override
    protected void onFinishInflate() {
	manageButton = (ImageButton) findViewById(R.id.manage);
    }

    public void setManageListener(OnClickListener listener) {
	manageButton.setOnClickListener(listener);
    }
}
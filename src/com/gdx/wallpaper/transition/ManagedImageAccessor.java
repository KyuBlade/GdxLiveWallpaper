package com.gdx.wallpaper.transition;

import com.badlogic.gdx.graphics.Color;
import com.gdx.wallpaper.image.ManagedImage;

import aurelienribon.tweenengine.TweenAccessor;

public class ManagedImageAccessor implements TweenAccessor<ManagedImage> {

    public static final int X = 1;
    public static final int Y = 2;
    public static final int XY = 3;
    public static final int ALPHA = 4;

    public ManagedImageAccessor() {
    }

    @Override
    public int getValues(ManagedImage target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case X:
//                returnValues[0] = target.getX();
                return 1;

            case Y:
//                returnValues[0] = target.getY();
                return 1;

            case XY:
//                returnValues[0] = target.getX();
//                returnValues[1] = target.getY();
                return 2;

            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;

            default:
                return 0;
        }
    }

    @Override
    public void setValues(ManagedImage target, int tweenType, float[] newValues) {
        switch (tweenType) {
//            case X:
//                target.setX(newValues[0]);
//                break;
//
//            case Y:
//                target.setY(newValues[1]);
//                break;
//
//            case XY:
//                target.setX(newValues[0]);
//                target.setY(newValues[1]);
//                break;

            case ALPHA:
                Color color = target.getColor();
                color.a = newValues[0];
                break;

            default:
                break;
        }
    }

}
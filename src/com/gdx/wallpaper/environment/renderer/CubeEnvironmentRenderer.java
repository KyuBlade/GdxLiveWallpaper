package com.gdx.wallpaper.environment.renderer;

import android.util.Log;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.wallpaper.environment.model.CubeModelInstance;
import com.gdx.wallpaper.environment.type.CubeEnvironment;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;

public class CubeEnvironmentRenderer extends AbstractEnvironmentRenderer<CubeEnvironment> {

    private CubeModelInstance modelInstance;
    private float rotation;
    private int lastScreen;

    public CubeEnvironmentRenderer(CubeEnvironment environment, ImageManager imageManager,
                                   Transition transition, Batch batch, Skin skin) {
        super(environment, new OrthographicCamera(), imageManager, transition, batch, skin);

        modelInstance = new CubeModelInstance();
        camera.far = 10000f;
        camera.update();
    }

    @Override
    protected void render(ModelBatch batch, float delta) {
        batch.render(modelInstance);
    }

    @Override
    protected void progressChange(int currentScreen, float progress) {
        final float oldRotation = rotation;
        rotation = ((currentScreen + progress) * 90f);
        modelInstance.transform.translate(0.5f, 0.5f, 0.5f);
        modelInstance.transform.rotate(Vector3.Y, rotation - oldRotation);
        modelInstance.transform.translate(-0.5f, -0.5f, -0.5f);

        if (lastScreen != currentScreen) {
            switch (lastScreen % 4) {
                case 0:
                    modelInstance.setTextureFront(null);
                    modelInstance.setTextureRight(null);
                    break;
                case 1:
                    modelInstance.setTextureRight(null);
                    modelInstance.setTextureBack(null);
                    break;
                case 2:
                    modelInstance.setTextureBack(null);
                    modelInstance.setTextureLeft(null);
                    break;
                case 3:
                    modelInstance.setTextureLeft(null);
                    modelInstance.setTextureFront(null);
                    break;
            }
        }

        int faceNumber = currentScreen % 4;
        Log.i("Face", "" + faceNumber);
        switch (faceNumber) {
            case 0:
                modelInstance.setTextureFront(from.getTexture());
                modelInstance.setTextureRight(to.getTexture());
                break;
            case 1:
                modelInstance.setTextureRight(from.getTexture());
                modelInstance.setTextureBack(to.getTexture());
                break;
            case 2:
                modelInstance.setTextureBack(from.getTexture());
                modelInstance.setTextureLeft(to.getTexture());
                break;
            case 3:
                modelInstance.setTextureLeft(from.getTexture());
                modelInstance.setTextureFront(to.getTexture());
                break;
        }

        lastScreen = currentScreen;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        modelInstance.transform.setToScaling(width, height, width);
        camera.position.z = 2000f;
        camera.update();
    }

    @Override
    public void dispose() {
        super.dispose();

        modelInstance.model.dispose();
    }
}
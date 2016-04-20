package com.gdx.wallpaper.environment.renderer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.wallpaper.environment.model.QuadModelInstance;
import com.gdx.wallpaper.environment.type.NullEnvironment;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;

public class NullEnvironmentRenderer extends AbstractEnvironmentRenderer<NullEnvironment> {

    private static final float threshold = 0.2f;

    private QuadModelInstance modelInstance;

    public NullEnvironmentRenderer(NullEnvironment environment, ImageManager imageManager,
                                   Transition transition, Batch batch, Skin skin) {
        super(environment, new OrthographicCamera(), imageManager, transition, batch, skin);

        modelInstance = new QuadModelInstance();
    }

    @Override
    protected void render(ModelBatch batch, float delta) {
        batch.render(modelInstance);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        modelInstance.transform.setToScaling(width, height, 1f);
    }

    @Override
    protected void progressChange(int currentScreen, float progress) {
        if (progress < 1f - threshold) {
            modelInstance.setTexture(from.getTexture());
        } else {
            modelInstance.setTexture(to.getTexture());
        }
    }

    @Override
    public void dispose() {
        super.dispose();

        modelInstance.model.dispose();
    }
}
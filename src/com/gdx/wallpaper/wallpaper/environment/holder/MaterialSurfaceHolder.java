package com.gdx.wallpaper.wallpaper.environment.holder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;

public class MaterialSurfaceHolder extends AbstractSurfaceHolder<Material> {

    public MaterialSurfaceHolder(Material material) {
        super(material);
    }

    @Override
    public void updateTexture(Texture texture) {
        surface.set(TextureAttribute.createDiffuse(texture));
    }

    @Override
    public void updateProgress(float newProgress) {

    }

    @Override
    public void onRemove() {

    }
}
package com.gdx.wallpaper.environment;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.environment.fragment.model.CubeEnvironmentModel;
import com.gdx.wallpaper.environment.fragment.model.EnvironmentModel;
import com.gdx.wallpaper.environment.fragment.model.NullEnvironmentModel;
import com.gdx.wallpaper.environment.fragment.model.PageCurlEnvironmentModel;
import com.gdx.wallpaper.environment.fragment.model.SlideEnvironmentModel;
import com.gdx.wallpaper.environment.fragment.model.SwapEnvironmentModel;
import com.gdx.wallpaper.environment.renderer.CubeEnvironmentRenderer;
import com.gdx.wallpaper.environment.renderer.AbstractEnvironmentRenderer;
import com.gdx.wallpaper.environment.renderer.NullEnvironmentRenderer;
import com.gdx.wallpaper.environment.renderer.PageCurlEnvironmentRenderer;
import com.gdx.wallpaper.environment.renderer.SlideEnvironmentRenderer;
import com.gdx.wallpaper.environment.renderer.SwapEnvironmentRenderer;
import com.gdx.wallpaper.environment.renderer.preview.CubeEnvironmentPreviewRenderer;
import com.gdx.wallpaper.environment.renderer.preview.EnvironmentPreviewRenderer;
import com.gdx.wallpaper.environment.renderer.preview.NullEnvironmentPreviewRenderer;
import com.gdx.wallpaper.environment.renderer.preview.PageCurlEnvironmentPreviewRenderer;
import com.gdx.wallpaper.environment.renderer.preview.SlideEnvironmentPreviewRenderer;
import com.gdx.wallpaper.environment.renderer.preview.SwapEnvironmentPreviewRenderer;
import com.gdx.wallpaper.environment.type.CubeEnvironment;
import com.gdx.wallpaper.environment.type.NullEnvironment;
import com.gdx.wallpaper.environment.type.PageCurlEnvironment;
import com.gdx.wallpaper.environment.type.SlideEnvironment;
import com.gdx.wallpaper.environment.type.SwapEnvironment;

public enum EnvironmentType {
    NONE(R.string.environment_type_none, NullEnvironment.class, NullEnvironmentRenderer.class,
         NullEnvironmentModel.class, NullEnvironmentPreviewRenderer.class, "null.frag"),
    SLIDE(R.string.environment_type_slide, SlideEnvironment.class, SlideEnvironmentRenderer.class,
          SlideEnvironmentModel.class, SlideEnvironmentPreviewRenderer.class, "slide.frag"),
    CUBE(R.string.environment_type_cube, CubeEnvironment.class, CubeEnvironmentRenderer.class,
         CubeEnvironmentModel.class, CubeEnvironmentPreviewRenderer.class, "cube.frag"),
    PAGE_CURL(R.string.environment_type_page_curl, PageCurlEnvironment.class, PageCurlEnvironmentRenderer.class,
              PageCurlEnvironmentModel.class, PageCurlEnvironmentPreviewRenderer.class,
              "pageCurl.frag"),
    SWAP(R.string.environment_type_swap, SwapEnvironment.class, SwapEnvironmentRenderer.class,
         SwapEnvironmentModel.class, SwapEnvironmentPreviewRenderer.class, "swap.frag");

    private int nameRes;
    private Class<? extends Environment> typeClass;
    private Class<? extends AbstractEnvironmentRenderer> rendererClass;
    private Class<? extends EnvironmentModel> modelClass;
    private Class<? extends EnvironmentPreviewRenderer> previewRendererClass;
    private String shader;

    EnvironmentType(int nameRes, Class<? extends Environment> typeClass,
                    Class<? extends AbstractEnvironmentRenderer> rendererClass,
                    Class<? extends EnvironmentModel> modelClass,
                    Class<? extends EnvironmentPreviewRenderer> previewRendererClass, String shader) {
        this.nameRes = nameRes;
        this.typeClass = typeClass;
        this.rendererClass = rendererClass;
        this.modelClass = modelClass;
        this.previewRendererClass = previewRendererClass;
        this.shader = shader;
    }

    public int getNameRes() {
        return nameRes;
    }

    public Class<? extends Environment> getTypeClass() {
        return typeClass;
    }

    public Class<? extends AbstractEnvironmentRenderer> getRendererClass() {
        return rendererClass;
    }

    public Class<? extends EnvironmentModel> getModelClass() {
        return modelClass;
    }

    public Class<? extends EnvironmentPreviewRenderer> getPreviewRendererClass() {
        return previewRendererClass;
    }

    public String getShader() {
        return shader;
    }
}
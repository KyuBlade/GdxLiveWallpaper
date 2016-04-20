package com.gdx.wallpaper.environment.model;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class DualQuadModelInstance extends ModelInstance {

    public DualQuadModelInstance() {
        super(new DualQuadModel().model);
    }

    static class DualQuadModel {

        private Model model;

        public DualQuadModel() {

        }
    }
}
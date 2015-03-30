package com.indignado.logicbricks.systems;

import box2dLight.RayHandler;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.indignado.logicbricks.components.LightComponent;

/**
 * @author Rubentxu
 */
public class LightRenderingSystem extends LogicBrickSystem {
    private Batch batch;
    private Camera camera;
    private ComponentMapper<LightComponent> lm;
    private RayHandler rayHandler;


    public LightRenderingSystem() {
        super(Family.all(LightComponent.class).get(), 5);
        lm = ComponentMapper.getFor(LightComponent.class);
        setProcessing(false);
    }


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        batch = context.get(Batch.class);
        camera = context.get(Camera.class);
        rayHandler = context.get(RayHandler.class);

    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.updateAndRender();

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {

    }


}

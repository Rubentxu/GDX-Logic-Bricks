package com.indignado.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.indignado.games.components.TextureBoundsComponent;
import com.indignado.games.components.TransformComponent;

/**
 * Created on 20/10/14.
 *
 * @author Rubentxu
 */
public class BoundsSystem extends IteratingSystem {
    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<TextureBoundsComponent> bm;


    public BoundsSystem() {
        super(Family.getFor(TextureBoundsComponent.class, TransformComponent.class));

        tm = ComponentMapper.getFor(TransformComponent.class);
        bm = ComponentMapper.getFor(TextureBoundsComponent.class);
    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent pos = tm.get(entity);
        TextureBoundsComponent bounds = bm.get(entity);

        bounds.bounds.x = pos.position.x - bounds.bounds.width * 0.5f;
        bounds.bounds.y = pos.position.y - bounds.bounds.height * 0.5f;

    }
}
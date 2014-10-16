package com.indignado.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.indignado.games.components.BoundsComponent;
import com.indignado.games.components.TransformComponent;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class BoundsSystem extends IteratingSystem {
    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<BoundsComponent> bm;


    public BoundsSystem() {
        super(Family.getFor(BoundsComponent.class, TransformComponent.class));
        tm = ComponentMapper.getFor(TransformComponent.class);
        bm = ComponentMapper.getFor(BoundsComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent pos = tm.get(entity);
        BoundsComponent bounds = bm.get(entity);

        bounds.center.x = pos.pos.x - bounds.extents.x;
        bounds.center.y = pos.pos.y - bounds.extents.y;

    }

}

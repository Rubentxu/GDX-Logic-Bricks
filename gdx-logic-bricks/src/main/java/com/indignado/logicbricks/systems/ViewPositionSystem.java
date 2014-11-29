package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.components.data.View;

/**
 * @author Rubentxu
 */
public class ViewPositionSystem extends IteratingSystem {
    private ComponentMapper<ViewsComponent> tm;


    public ViewPositionSystem() {
        super(Family.all(ViewsComponent.class, StateComponent.class).get(), 0);
        tm = ComponentMapper.getFor(ViewsComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        ViewsComponent viewsComponent = tm.get(entity);
        for (View view : viewsComponent.views) {
            if (view.attachedTransform != null) {
                view.setPosition(view.attachedTransform.getPosition());
                view.setRotation(MathUtils.radiansToDegrees * view.attachedTransform.getRotation());
            }
            if (view.localPosition != null) {
                view.setPosition(view.position.cpy());
                view.position.add(view.localPosition);
            }

        }

    }

}
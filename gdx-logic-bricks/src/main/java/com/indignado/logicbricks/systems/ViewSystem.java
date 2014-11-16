package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.data.AnimationView;
import com.indignado.logicbricks.data.ParticleEffectView;
import com.indignado.logicbricks.data.View;

/**
 * @author Rubentxu
 */
public class ViewSystem extends IteratingSystem {
    private ComponentMapper<ViewsComponent> tm;


    public ViewSystem() {
        super(Family.all(ViewsComponent.class, StateComponent.class).get(), 0);
        tm = ComponentMapper.getFor(ViewsComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        ViewsComponent viewsComponent = tm.get(entity);
        for (View view : viewsComponent.views) {
            if (view.attachedTransform != null) {
               view.position = view.attachedTransform.getPosition();
               view.rotation = MathUtils.radiansToDegrees * view.attachedTransform.getRotation();
            }
            if(view.localPosition != null) {
                view.position.add(view.localPosition);
            }
        }

    }

}

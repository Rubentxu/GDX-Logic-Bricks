package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.data.AnimationView;
import com.indignado.logicbricks.data.View;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class AnimationSystem extends IteratingSystem {
    private ComponentMapper<ViewsComponent> tm;
    private ComponentMapper<StateComponent> sm;


    public AnimationSystem() {
        super(Family.all(ViewsComponent.class, StateComponent.class).get(), 0);
        tm = ComponentMapper.getFor(ViewsComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        ViewsComponent viewsComponent = tm.get(entity);
        StateComponent state = sm.get(entity);
        for (View view : viewsComponent.views) {
            if (view instanceof AnimationView) {
                AnimationView animationView = ((AnimationView) view);
                Animation animation = animationView.animations.get(state.getCurrentState());
                if (animation != null) {
                    animationView.textureRegion = animation.getKeyFrame(state.time);

                }

            }
        }

    }

}

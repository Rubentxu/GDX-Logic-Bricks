package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.data.View;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class ViewsSystem extends IteratingSystem {
    private ComponentMapper<ViewsComponent> tm;
    private ComponentMapper<StateComponent> sm;


    public ViewsSystem() {
        super(Family.all(ViewsComponent.class, StateComponent.class).get(), 0);
        tm = ComponentMapper.getFor(ViewsComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        ViewsComponent viewsComponent = tm.get(entity);
        StateComponent state = sm.get(entity);
        Gdx.app.log("ViewSystem", "enter: ");
        for (View view : viewsComponent.views) {
            if (view.animations != null) {
                Animation animation = view.animations.get(state.get());
                if (animation != null) {
                    Gdx.app.log("ViewSystem", "state: "+ state.get() + " state time: "+ state.time + " animation size: "+ animation.getKeyFrames().length);
                    view.textureRegion = animation.getKeyFrame(state.time);
                }

            }
        }

    }

}

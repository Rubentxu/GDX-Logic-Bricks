package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.config.Settings;
import com.indignado.logicbricks.core.data.AnimationView;
import com.indignado.logicbricks.core.data.View;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class AnimationViewSystem extends LogicBrickSystem {
    private ComponentMapper<ViewsComponent> tm;
    private ComponentMapper<StateComponent> sm;


    public AnimationViewSystem() {
        super(Family.all(ViewsComponent.class, StateComponent.class).get(), 4);
        tm = ComponentMapper.getFor(ViewsComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.DEBUG_ENTITY != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        ViewsComponent viewsComponent = tm.get(entity);
        StateComponent state = sm.get(entity);
        for (Object object : viewsComponent.views) {
            View view = (View) object;

            if (view instanceof AnimationView) {
                AnimationView animationView = ((AnimationView) view);
                Animation animation = animationView.animations.get(state.getCurrentState());
                if (animation != null) {
                    animationView.setTextureRegion(animation.getKeyFrame(state.time));

                }

            }

        }

    }


}

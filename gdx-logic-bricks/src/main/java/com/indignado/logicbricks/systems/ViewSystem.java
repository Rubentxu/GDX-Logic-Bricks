package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.data.AnimationView;
import com.indignado.logicbricks.core.data.View;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class ViewSystem extends LogicBrickSystem {
    private ComponentMapper<ViewsComponent> tm;
    private ComponentMapper<StateComponent> sm;
    private float alpha;


    public ViewSystem() {
        super(Family.all(ViewsComponent.class, StateComponent.class).get(), 4);
        tm = ComponentMapper.getFor(ViewsComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.debugEntity != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        ViewsComponent viewsComponent = tm.get(entity);
        StateComponent state = sm.get(entity);
        for (Object object : viewsComponent.views) {
            View view = (View) object;
            if (view.attachedTransform != null) {
                Vector2 bodyPosition = view.attachedTransform.getPosition();
                float bodyAngle = MathUtils.radiansToDegrees * view.attachedTransform.getRotation();

                view.position.x = bodyPosition.x * alpha + view.position.x * (1.0f - alpha);
                view.position.y = bodyPosition.y * alpha + view.position.x * (1.0f - alpha);
                view.rotation = (bodyAngle * alpha + view.rotation * (1.0f - alpha));

            }
            if (view.localPosition != null) {
                view.setPosition(view.position.cpy());
                view.position.add(view.localPosition);
            }

            if (view instanceof AnimationView) {
                AnimationView animationView = ((AnimationView) view);
                Animation animation = animationView.animations.get(state.getCurrentState());
                if (animation != null) {
                    animationView.setTextureRegion(animation.getKeyFrame(state.time));

                }

            }

        }

    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        alpha = 1.0f;

    }


    public void setAlpha(float alpha) {
        //this.alpha = alpha;

    }

}

package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.components.data.View;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class ViewPositionSystem extends IteratingSystem {
    private String tag = this.getClass().getSimpleName();
    private ComponentMapper<ViewsComponent> tm;
    private float alpha;


    public ViewPositionSystem() {
        super(Family.all(ViewsComponent.class, StateComponent.class).get(), 4);
        tm = ComponentMapper.getFor(ViewsComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.debugEntity != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        ViewsComponent viewsComponent = tm.get(entity);
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

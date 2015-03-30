package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.components.TransformsComponent;
import com.indignado.logicbricks.config.Settings;
import com.indignado.logicbricks.core.data.Transform;
import com.indignado.logicbricks.core.data.Transform2D;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class TransformSystem extends LogicBrickSystem {
    private ComponentMapper<TransformsComponent> t2D;
    private float alpha;
    private Body body;
    private Array<Transform2D> rootParents;

    public TransformSystem() {
        super(Family.all(TransformsComponent.class).get(), 4);
        t2D = ComponentMapper.getFor(TransformsComponent.class);
        alpha = 1;
        rootParents = new Array<>();

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.DEBUG_ENTITY != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        TransformsComponent transforms2D = t2D.get(entity);

        for (Transform transform: transforms2D.transforms) {
            updateTransform2D((Transform2D) transform);
        }

    }


    private void updateTransform2D(Transform2D transform) {
        if (transform.rigidBody != null) {
            this.body = transform.rigidBody.body;

            update2DPosition(body.getPosition(), transform);
            transform.roll = MathUtils.radiansToDegrees * body.getAngle();
            transform.bounds.setSize(transform.scaleX, transform.scaleY);
            transform.bounds.setCenter(transform.x, transform.y);
            transform.pivot.x = transform.bounds.getWidth() / 2;
            transform.pivot.y = transform.bounds.getHeight() / 2;

        }

    }


    private void update2DPosition(Vector2 bodyPosition, Transform2D position) {
        position.x = bodyPosition.x * alpha + position.x * (1.0f - alpha);
        position.y = bodyPosition.y * alpha + position.y * (1.0f - alpha);

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

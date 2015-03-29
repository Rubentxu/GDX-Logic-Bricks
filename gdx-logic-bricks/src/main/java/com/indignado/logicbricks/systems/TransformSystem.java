package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
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
    private Quaternion orientation;
    private Body body;

    public TransformSystem() {
        super(Family.all(TransformsComponent.class).get(), 4);
        t2D = ComponentMapper.getFor(TransformsComponent.class);
        orientation = new Quaternion();
        alpha = 1;

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.DEBUG_ENTITY != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        TransformsComponent transforms2D = t2D.get(entity);

        for (Transform transform : transforms2D.transforms) {
            if(transform instanceof Transform2D) updateTransform2D((Transform2D) transform);


        }

    }


    private void updateTransform2D(Transform2D transform) {
        if (transform.rigidBody != null) {
            this.body = transform.rigidBody.body;
            orientation.idt();

            update2DPosition(body.getPosition(), transform);
            orientation.setEulerAnglesRad(transform.yaw, transform.pitch, body.getAngle());
            transform.yaw = orientation.getYaw();
            transform.pitch = orientation.getPitch();
            transform.roll = orientation.getRoll();
            transform.bounds.setCenter(transform.x, transform.y);

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

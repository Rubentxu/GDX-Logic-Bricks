package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.Transforms2DComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.config.Settings;
import com.indignado.logicbricks.core.data.Transform2D;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class Transform2DSystem extends LogicBrickSystem {
    private ComponentMapper<Transforms2DComponent> t2D;
    private float alpha;
    private Quaternion orientation;
    private Body body;
    private Vector3 position;


    public Transform2DSystem() {
        super(Family.all(ViewsComponent.class, StateComponent.class).get(), 4);
        t2D = ComponentMapper.getFor(Transforms2DComponent.class);
        orientation = new Quaternion();
        position = new Vector3();

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.DEBUG_ENTITY != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        Transforms2DComponent transforms2D = t2D.get(entity);

        for (Transform2D transform : transforms2D.transforms) {
            if (transform.rigidBody != null) {
                this.body = transform.rigidBody.body;
                orientation.idt();
                position.setZero();

                transform.matrix.getTranslation(position);
                updatePosition(body.getPosition(), position);
                orientation.setEulerAnglesRad(0, 0, body.getAngle());
                transform.matrix.set(position, orientation);

            }

        }

    }


    private Vector3 updatePosition(Vector2 bodyPosition, Vector3 position) {
        position.x = bodyPosition.x * alpha + position.x * (1.0f - alpha);
        position.y = bodyPosition.y * alpha + position.y * (1.0f - alpha);
        return position;
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

package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.config.Settings;
import com.indignado.logicbricks.utils.Log;
import com.indignado.logicbricks.utils.builders.LBBuilders;

/**
 * @author Rubentxu
 */
public class DraggableBodySystem extends LogicBrickSystem implements InputProcessor {
    private MouseJointDef jointDef;
    private MouseJoint joint;
    private Vector3 tmp = new Vector3();
    private Vector2 tmp2 = new Vector2();
    private Camera camera;
    private World physics;
    private QueryCallback queryCallback = new QueryCallback() {

        @Override
        public boolean reportFixture(Fixture fixture) {
            if (!fixture.testPoint(tmp.x, tmp.y))
                return true;

            jointDef.bodyB = fixture.getBody();
            jointDef.target.set(tmp.x, tmp.y);
            joint = (MouseJoint) context.get(World.class).createJoint(jointDef);
            return false;
        }

    };



    public DraggableBodySystem() {
        super(Family.all(StateComponent.class).get());

    }



    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.camera = context.get(Camera.class);
        this.physics = context.get(World.class);
        if (Settings.DRAGGABLE_BOX2D_BODIES) {
            jointDef = new MouseJointDef();
            jointDef.bodyA = context.get(LBBuilders.class).getBodyBuilder().build();
            jointDef.collideConnected = true;
            jointDef.maxForce = Settings.DRAGGABLE_BOX2D_MAX_FORCE;

        } else {
            Log.error(tag, "A reference not set up a draggableRefBody");

        }

    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (Settings.DRAGGABLE_BOX2D_BODIES) {
            camera.unproject(tmp.set(screenX, screenY, 0));
            physics.QueryAABB(queryCallback, tmp.x, tmp.y, tmp.x, tmp.y);

        }
        return false;

    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (Settings.DRAGGABLE_BOX2D_BODIES) {
            if (joint == null)
                return false;

            camera.unproject(tmp.set(screenX, screenY, 0));
            joint.setTarget(tmp2.set(tmp.x, tmp.y));

        }

        return false;

    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (Settings.DRAGGABLE_BOX2D_BODIES) {
            if (joint == null)
                return false;

            physics.destroyJoint(joint);
            joint = null;

        }

        return false;

    }


    public boolean keyDown(int keycode) {
        return false;

    }


    public boolean keyUp(int keycode) {
        return false;

    }


    public boolean keyTyped(char character) {
        return false;

    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;

    }


    @Override
    public boolean scrolled(int amount) {
        return false;

    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

}




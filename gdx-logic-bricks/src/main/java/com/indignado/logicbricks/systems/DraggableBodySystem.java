package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class DraggableBodySystem extends LogicBrickSystem implements InputProcessor {
    private MouseJointDef jointDef;
    private MouseJoint joint;
    private Vector3 tmp = new Vector3();
    private Vector2 tmp2 = new Vector2();
    private QueryCallback queryCallback = new QueryCallback() {

        @Override
        public boolean reportFixture(Fixture fixture) {
            if (!fixture.testPoint(tmp.x, tmp.y))
                return true;

            jointDef.bodyB = fixture.getBody();
            jointDef.target.set(tmp.x, tmp.y);
            joint = (MouseJoint) game.getPhysics().createJoint(jointDef);
            return false;
        }

    };


    public DraggableBodySystem() {
        super(null);
        if (Settings.draggableBodies) {
            jointDef = new MouseJointDef();
            jointDef.bodyA = game.getBodyBuilder().build();
            jointDef.collideConnected = true;
            jointDef.maxForce = Settings.draggableMaxForce;

        } else {
            Log.error(tag, "A reference not set up a draggableRefBody");

        }

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (Settings.draggableBodies) {
            game.getCamera().unproject(tmp.set(screenX, screenY, 0));
            game.getPhysics().QueryAABB(queryCallback, tmp.x, tmp.y, tmp.x, tmp.y);

        }
        return false;

    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (Settings.draggableBodies) {
            if (joint == null)
                return false;

            game.getCamera().unproject(tmp.set(screenX, screenY, 0));
            joint.setTarget(tmp2.set(tmp.x, tmp.y));

        }

        return false;

    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (Settings.draggableBodies) {
            if (joint == null)
                return false;

            game.getPhysics().destroyJoint(joint);
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




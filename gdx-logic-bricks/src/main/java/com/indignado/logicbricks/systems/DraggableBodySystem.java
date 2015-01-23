package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.utils.Log;
import com.indignado.logicbricks.utils.builders.BodyBuilder;

/**
 * @author Rubentxu
 */
public class DraggableBodySystem extends EntitySystem implements InputProcessor {
    private String tag = this.getClass().getSimpleName();
    private Game game;
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


    public DraggableBodySystem(Game game) {
        this.game = game;
        if (Settings.draggableBodies) {
            BodyBuilder bodyBuilder = game.getBodyBuilder();

            jointDef = new MouseJointDef();
            jointDef.bodyA = Settings.draggableRefBody;
            jointDef.collideConnected = true;
            jointDef.maxForce = Settings.draggableMaxForce;
        } else {
            Log.error(tag, "A reference not set up a draggableRefBody");

        }

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (Settings.draggableBodies && Settings.draggableRefBody != null) {
            game.getCamera().unproject(tmp.set(screenX, screenY, 0));
            game.getPhysics().QueryAABB(queryCallback, tmp.x, tmp.y, tmp.x, tmp.y);

        }
        return false;

    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (Settings.draggableBodies && Settings.draggableRefBody != null) {
            if (joint == null)
                return false;

            game.getCamera().unproject(tmp.set(screenX, screenY, 0));
            joint.setTarget(tmp2.set(tmp.x, tmp.y));

        }

        return false;

    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (Settings.draggableBodies && Settings.draggableRefBody != null) {
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

}




package com.indignado.functional.test;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.indignado.functional.test.base.LogicBricksTest;
import com.indignado.functional.test.levels.flyingDart.FlyingDartCollisionRule;
import com.indignado.functional.test.levels.flyingDart.FlyingDartLevel;
import com.indignado.logicbricks.config.Settings;
import com.indignado.logicbricks.systems.sensors.CollisionSensorSystem;
import com.indignado.logicbricks.utils.Log;


/**
 * @author Rubentxu.
 */
public class FlyingDartTest extends LogicBricksTest {
    private String tag = this.getClass().getSimpleName();
    private FlyingDartCollisionRule flyingDartCollisionRule;


    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;

        new LwjglApplication(new FlyingDartTest(), config);
    }

    @Override
    public void create() {
        super.create();
        addLevel(new FlyingDartLevel(engine, builders, assetManager));
        flyingDartCollisionRule = new FlyingDartCollisionRule();

        CollisionSensorSystem collisionSensorSystem = new CollisionSensorSystem();
        collisionSensorSystem.addCollisionRule(flyingDartCollisionRule);
        engine.addSystem(collisionSensorSystem);
        Settings.drawFPSPosX = -12.0f;
        Settings.drawFPSPosY = 18.0f;

    }


    @Override
    public void render() {
        super.render();
        //game.getPhysics().clearForces();

        for (WeldJointDef jointDef : flyingDartCollisionRule.jointDefs) {
            Log.debug(tag, "CreateJoint");
            physics.createJoint(jointDef);

        }
        flyingDartCollisionRule.jointDefs.clear();

        for (Joint joint : flyingDartCollisionRule.destroyJoints) {
            Log.debug(tag, "DestroyJoint");
            physics.destroyJoint(joint);

        }
        flyingDartCollisionRule.destroyJoints.clear();

    }

}

package com.indignado.functional.test;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.indignado.functional.test.base.LogicBricksTest;
import com.indignado.functional.test.levels.FlyingDartLevel;
import com.indignado.logicbricks.systems.sensors.CollisionSensorSystem;
import com.indignado.logicbricks.utils.builders.BodyBuilder;


/**
 * @author Rubentxu.
 */
public class FlyingDartTest extends LogicBricksTest {
    private Body ground;
    private ParticleEffect dustEffect;
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
        addLevel(new FlyingDartLevel());
        flyingDartCollisionRule = new FlyingDartCollisionRule();
        CollisionSensorSystem collisionSensorSystem = world.getEngine().getSystem(CollisionSensorSystem.class);
        collisionSensorSystem.addCollisionRule(flyingDartCollisionRule);

    }


    @Override
    public void render() {
        super.render();
        world.getPhysics().clearForces();

        for (WeldJointDef jointDef : flyingDartCollisionRule.jointDefs) {
            Gdx.app.log("FlyingDartTest", "createJoint");
            world.getPhysics().createJoint(jointDef);
        }

    }

}

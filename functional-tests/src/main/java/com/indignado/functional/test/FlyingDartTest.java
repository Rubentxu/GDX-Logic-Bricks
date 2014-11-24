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
import com.indignado.logicbricks.systems.sensors.CollisionSensorSystem;
import com.indignado.logicbricks.utils.box2d.BodyBuilder;


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
    protected void createWorld(World world, Engine engine) {
        this.world = world;
        this.engine = engine;
        bodyBuilder = new BodyBuilder(world);
        camera.position.set(0, 9, 0);

        wall(15, 7.5F
                , 1, 20);

        ground = bodyBuilder.fixture(bodyBuilder.fixtureDefBuilder()
                .boxShape(50, 1))
                .type(BodyDef.BodyType.StaticBody)
                .position(0, 0)
                .mass(1)
                .build();


        //engine.addEntity(createArrow(new Vector2(-12, 3), 150));
        // engine.addEntity(createDummy(new Vector2(-14, 2f)));

        flyingDartCollisionRule = new FlyingDartCollisionRule();
        CollisionSensorSystem collisionSensorSystem = new CollisionSensorSystem();
        collisionSensorSystem.addCollisionRule(flyingDartCollisionRule);
        engine.addSystem(collisionSensorSystem);


    }


    @Override
    public void render() {
        super.render();
        world.clearForces();

        for (WeldJointDef jointDef : flyingDartCollisionRule.jointDefs) {
            Gdx.app.log("FlyingDartTest", "createJoint");
            world.createJoint(jointDef);
        }

    }
}

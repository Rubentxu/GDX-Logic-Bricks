package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.indignado.logicbricks.bricks.sensors.CollisionSensor;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.utils.box2d.BodyBuilder;
import com.indignado.logicbricks.utils.logicbricks.LogicBricksBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSensorSystemTest {
    private World physic;
    private PooledEngine engine;
    private CollisionSensorSystem collisionSensorSystem;
    private BodyBuilder bodyBuilder;
    private Entity player;
    private Body body;
    private Body body2;
    private Entity enemy;
    private RigidBodiesComponents rigidBodiesComponents;
    private RigidBodiesComponents rigidBodiesComponents2;
    private int statePrueba;


    @Before
    public void setup() {
        GdxNativesLoader.load();
        physic = new World(new Vector2(0, -9.81f), true);

        engine = new PooledEngine();
        collisionSensorSystem = new CollisionSensorSystem();
        physic.setContactListener(collisionSensorSystem);
        bodyBuilder = new BodyBuilder(physic);
        this.statePrueba = 1;

    }


    private void createContext() {
        player = engine.createEntity();
        body = bodyBuilder
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .circleShape(1)
                        .restitution(0f))
                .position(40, 23)
                .mass(1f)
                .type(BodyDef.BodyType.DynamicBody)
                .userData(player)
                .build();

        rigidBodiesComponents = new RigidBodiesComponents();
        rigidBodiesComponents.rigidBodies.add(body);

        StateComponent stateComponent = new StateComponent();
        stateComponent.set(1);

        player.add(rigidBodiesComponents);
        player.add(stateComponent);

        engine.addEntity(player);

        enemy = engine.createEntity();
        body2 = bodyBuilder
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .circleShape(1 * 0.1f)
                        .restitution(0f))
                .position(40, 20)
                .mass(1f)
                .type(BodyDef.BodyType.StaticBody)
                .userData(player)
                .build();


        rigidBodiesComponents2 = new RigidBodiesComponents();
        rigidBodiesComponents2.rigidBodies.add(body2);

        enemy.add(rigidBodiesComponents2);

        engine.addEntity(enemy);

    }


    @Test
    public void bodyCollidesBodyTest() {
        createContext();
        CollisionSensor collisionSensor = new CollisionSensor();
        collisionSensor.targetBody = body2;
        collisionSensor.ownerRigidBody = body;

        new LogicBricksBuilder(player).addSensor(collisionSensor, statePrueba);

        engine.addSystem(collisionSensorSystem);
        engine.update(1);

        System.out.println("Bodies size: " + physic.getBodyCount());
        physic.step(1, 8, 3);

        assertTrue(collisionSensor.pulseSignal);

    }


    @Test
    public void bodyCollidesFixtureTest() {
        createContext();
        CollisionSensor collisionSensor = new CollisionSensor();
        collisionSensor.targetFixture = body2.getFixtureList().first();
        collisionSensor.ownerRigidBody = body;

        new LogicBricksBuilder(player).addSensor(collisionSensor, statePrueba);

        engine.addSystem(collisionSensorSystem);
        engine.update(1);

        System.out.println("Bodies size: " + physic.getBodyCount());
        physic.step(1, 8, 3);

        assertTrue(collisionSensor.pulseSignal);

    }


    @Test
    public void fixtureCollidesBodyTest() {
        createContext();
        CollisionSensor collisionSensor = new CollisionSensor();
        collisionSensor.targetBody = body2;
        collisionSensor.ownerFixture = body.getFixtureList().first();

        new LogicBricksBuilder(player).addSensor(collisionSensor, statePrueba);

        engine.addSystem(collisionSensorSystem);
        engine.update(1);


        System.out.println("Bodies size: " + physic.getBodyCount());
        physic.step(1, 8, 3);

        assertTrue(collisionSensor.pulseSignal);

    }


    @Test
    public void fixtureCollidesFixtureTest() {
        createContext();
        CollisionSensor collisionSensor = new CollisionSensor();
        collisionSensor.targetFixture = body2.getFixtureList().first();
        collisionSensor.ownerFixture = body.getFixtureList().first();

        new LogicBricksBuilder(player).addSensor(collisionSensor, statePrueba);

        engine.addSystem(collisionSensorSystem);
        engine.update(1);

        System.out.println("Bodies size: " + physic.getBodyCount());
        physic.step(1, 8, 3);

        engine.update(1);

        assertTrue(collisionSensor.pulseSignal);

    }


    @Test
    public void bodyCollisionFalseTest() {
        createContext();
        CollisionSensor collisionSensor = new CollisionSensor();
        collisionSensor.targetBody = body2;
        collisionSensor.ownerFixture = body.getFixtureList().first();

        new LogicBricksBuilder(player).addSensor(collisionSensor, statePrueba);

        engine.addSystem(collisionSensorSystem);
        engine.update(1);

        System.out.println("Bodies size: " + physic.getBodyCount());
        physic.step(0.1f, 8, 3);

        assertFalse(collisionSensor.pulseSignal);

    }


    @Test
    public void fixtureCollisionFalseTest() {
        createContext();
        CollisionSensor collisionSensor = new CollisionSensor();
        collisionSensor.targetFixture = body2.getFixtureList().first();
        collisionSensor.ownerFixture = body.getFixtureList().first();

        new LogicBricksBuilder(player).addSensor(collisionSensor, statePrueba);

        engine.addSystem(collisionSensorSystem);
        engine.update(1);

        System.out.println("Bodies size: " + physic.getBodyCount());
        physic.step(0.1f, 8, 3);

        assertFalse(collisionSensor.pulseSignal);

    }


    @Test
    public void endFixtureCollisionTest() {
        createContext();
        CollisionSensor collisionSensor = new CollisionSensor();
        collisionSensor.targetFixture = body2.getFixtureList().first();
        collisionSensor.ownerFixture = body.getFixtureList().first();

        new LogicBricksBuilder(player).addSensor(collisionSensor, statePrueba);

        engine.addSystem(collisionSensorSystem);
        engine.update(1);

        System.out.println("Body position: " + body.getPosition());
        physic.step(1f, 8, 3);
        System.out.println("Body position2: " + body.getPosition());

        assertTrue(collisionSensor.pulseSignal);

        body.applyForce(0, 60, body.getWorldCenter().x, body.getWorldCenter().y, true);

        physic.step(1f, 8, 3);
        System.out.println("Body position3: " + body.getPosition());

        physic.step(1f, 8, 3);
        System.out.println("Body position4: " + body.getPosition());
        assertFalse(collisionSensor.pulseSignal);

    }


    @Test
    public void endBodyCollisionTest() {
        createContext();
        CollisionSensor collisionSensor = new CollisionSensor();
        collisionSensor.targetBody = body2;
        collisionSensor.ownerFixture = body.getFixtureList().first();

        new LogicBricksBuilder(player).addSensor(collisionSensor, statePrueba);

        engine.addSystem(collisionSensorSystem);
        engine.update(1);

        System.out.println("Body position: " + body.getPosition());
        physic.step(1f, 8, 3);
        System.out.println("Body position2: " + body.getPosition());

        assertTrue(collisionSensor.pulseSignal);

        body.applyForce(0, 60, body.getWorldCenter().x, body.getWorldCenter().y, true);

        physic.step(1f, 8, 3);
        System.out.println("Body position3: " + body.getPosition());

        physic.step(1f, 8, 3);
        System.out.println("Body position4: " + body.getPosition());
        assertFalse(collisionSensor.pulseSignal);

    }


}
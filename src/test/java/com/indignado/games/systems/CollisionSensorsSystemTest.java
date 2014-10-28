package com.indignado.games.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.indignado.games.bricks.sensors.CollisionSensor;
import com.indignado.games.components.ColliderComponent;
import com.indignado.games.components.RigidBodiesComponents;
import com.indignado.games.components.sensors.CollisionSensorComponent;
import com.indignado.games.utils.box2d.BodyBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSensorsSystemTest {
    private CollisionSensorsSystem system;
    private Entity entity;
    private World physic;
    private Set<ColliderComponent> fixtureTest;
    private PooledEngine engine;
    private CollisionSensorComponent collisionSensorComponent;
    private CollisionSensorsSystem collisionSensorsSystem;
    private BodyBuilder bodyBuilder;


    @Before
    public void setup() {
        GdxNativesLoader.load();
        physic = new World(new Vector2(0, -9.81f), true);

        engine = new PooledEngine();
        collisionSensorsSystem = new CollisionSensorsSystem();
        physic.setContactListener(collisionSensorsSystem);


        collisionSensorComponent = new CollisionSensorComponent();
        bodyBuilder = new BodyBuilder(physic);
    }


    @Test
    public void collisionTest() {
        Entity player = engine.createEntity();
        Body body = bodyBuilder
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .circleShape(1 * 0.1f)
                        .restitution(0f))
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .circleShape(1)
                        .sensor())
                .position(40, 22)
                .mass(1f)
                .type(BodyDef.BodyType.DynamicBody)
                .userData(player)
                .build();

        RigidBodiesComponents rigidBodiesComponents = new RigidBodiesComponents();
        rigidBodiesComponents.rigidBodies.add(body);

        player.add(rigidBodiesComponents);

        engine.addEntity(player);

        Entity enemy = engine.createEntity();
        Body body2 = bodyBuilder
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .circleShape(1 * 0.1f)
                        .restitution(0f))
                .position(40, 20)
                .mass(1f)
                .type(BodyDef.BodyType.StaticBody)
                .userData(player)
                .build();

        RigidBodiesComponents rigidBodiesComponents2 = new RigidBodiesComponents();
        rigidBodiesComponents2.rigidBodies.add(body2);

        enemy.add(rigidBodiesComponents2);

        engine.addEntity(enemy);

        CollisionSensorComponent collisionSensorComponent =  new CollisionSensorComponent();
        CollisionSensor collisionSensor = new CollisionSensor(player);
        collisionSensor.targetBody = body2;
        collisionSensor.fixture = body.getFixtureList().first();


        collisionSensorComponent.collisionSensors.add(collisionSensor);
        player.add(collisionSensorComponent);


        engine.addSystem(collisionSensorsSystem);

        System.out.println("Bodies size: " + physic.getBodyCount());

        float deltaTime = 1;
        engine.update(deltaTime);
        physic.step(1, 8, 3);
        physic.step(1, 8, 3);



       // assertEquals(deltaTime, stateComponent.time, 0.1);

    }







}

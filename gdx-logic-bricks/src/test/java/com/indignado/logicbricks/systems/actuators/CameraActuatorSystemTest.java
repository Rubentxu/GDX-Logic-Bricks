package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.LogicBricksException;
import com.indignado.logicbricks.core.actuators.CameraActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.AlwaysSensor;
import com.indignado.logicbricks.systems.StateSystem;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Rubentxu.
 */
public class CameraActuatorSystemTest {
    private PooledEngine engine;
    private String StatePruebas = "StatePruebas";
    private String name;
    private Entity entity;
    private World physic;
    private CameraActuator cameraActuator;
    private Body body;
    private EntityBuilder entityBuilder;


    @Before
    public void setup() {
        GdxNativesLoader.load();
        physic = new World(new Vector2(0, -9.81f), true);

        this.name = "BricksPruebas";
        this.entity = new Entity();
        engine = new PooledEngine();
        engine.addSystem(new CameraActuatorSystem());
        engine.addSystem(new StateSystem(engine));

        BodyBuilder bodyBuilder = new BodyBuilder(physic);
        body = bodyBuilder
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .circleShape(1)
                        .restitution(0f))
                .position(40, 23)
                .mass(1f)
                .type(BodyDef.BodyType.DynamicBody)
                .userData(entity)
                .build();

        RigidBodiesComponents rigidBodiesComponents = new RigidBodiesComponents();
        //rigidBodiesComponents.rigidBodies.add(body);

        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.getState("StatePruebas"));

        entity.add(rigidBodiesComponents);
        entity.add(stateComponent);

        engine.addEntity(entity);
        entityBuilder = new EntityBuilder(engine);

    }


    @Test
    public void cameraActuatorTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor();
        ConditionalController conditionalController = new ConditionalController();
        conditionalController.pulseSignal = true;

        cameraActuator = new CameraActuator();
        cameraActuator.controllers.add(conditionalController);
        cameraActuator.camera = new OrthographicCamera();
        cameraActuator.height = (short) 5;

        entityBuilder.addController(conditionalController, StatePruebas)
                .connectToSensor(alwaysSensor)
                .connectToActuator(cameraActuator);

        engine.update(1);

        assertEquals(body.getPosition().x, cameraActuator.camera.position.x, 0);
        assertEquals(body.getPosition().y, cameraActuator.camera.position.y, 0);

    }


    @Test(expected = LogicBricksException.class)
    public void cameraActuatorExceptionTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor();
        ConditionalController conditionalController = new ConditionalController();
        conditionalController.pulseSignal = true;

        cameraActuator = new CameraActuator();
        cameraActuator.camera = new OrthographicCamera();
        cameraActuator.height = (short) 5;


        entityBuilder.addController(conditionalController, StatePruebas)
                .connectToSensor(alwaysSensor)
                .connectToActuator(cameraActuator);


        engine.update(1);

    }

}
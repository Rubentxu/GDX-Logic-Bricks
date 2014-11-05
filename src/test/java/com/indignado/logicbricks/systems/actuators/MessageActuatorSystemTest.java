package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.indignado.logicbricks.bricks.actuators.CameraActuator;
import com.indignado.logicbricks.bricks.actuators.MessageActuator;
import com.indignado.logicbricks.bricks.controllers.AndController;
import com.indignado.logicbricks.bricks.exceptions.LogicBricksException;
import com.indignado.logicbricks.bricks.sensors.AlwaysSensor;
import com.indignado.logicbricks.components.LogicBricksComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.systems.StateSystem;
import com.indignado.logicbricks.utils.box2d.BodyBuilder;
import com.indignado.logicbricks.utils.logicbricks.LogicBricksComponentBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Rubentxu.
 */
public class MessageActuatorSystemTest {
    private PooledEngine engine;
    private String state;
    private String name;
    private Entity entity;
    private MessageActuator messageActuator;

    private int MENSAJE_PRUEBAS = 1;
    private Boolean checkMessage = false;


    @Before
    public void setup() {
        this.name = "BricksPruebas";
        this.state = "StatePruebas";
        this.entity = new Entity();
        engine = new PooledEngine();
        engine.addSystem(new MessageActuatorSystem());
        engine.addSystem(new StateSystem());

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        entity.add(stateComponent);
        engine.addEntity(entity);

    }


    @Test
    public void messageActuatorTest() {
        AndController andController = new AndController();
        andController.pulseSignal = true;
        MessageDispatcher.getInstance().addListener(new Telegraph() {
            @Override
            public boolean handleMessage(Telegram msg) {
                checkMessage = true;
                return true;
            }
        },MENSAJE_PRUEBAS);

        messageActuator = new MessageActuator();
        messageActuator.controllers.add(andController);
        messageActuator.message = MENSAJE_PRUEBAS;

        LogicBricksComponent lbc =  new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addController(andController)
                .addActuator(messageActuator)
                .build();


        entity.add(lbc);
        engine.update(1);

        assertTrue(checkMessage);


    }



}
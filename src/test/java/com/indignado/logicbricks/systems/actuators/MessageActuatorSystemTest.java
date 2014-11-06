package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.indignado.logicbricks.bricks.actuators.MessageActuator;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;
import com.indignado.logicbricks.components.LogicBricksComponent;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.systems.StateSystem;
import org.junit.Before;
import org.junit.Test;

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

        StateComponent stateComponent = new StateComponent();
        stateComponent.set(state);

        entity.add(stateComponent);
        engine.addEntity(entity);

    }


    @Test
    public void messageActuatorTest() {
        ConditionalController conditionalController = new ConditionalController();
        conditionalController.pulseSignal = true;
        MessageDispatcher.getInstance().addListener(new Telegraph() {
            @Override
            public boolean handleMessage(Telegram msg) {
                checkMessage = true;
                return true;
            }
        }, MENSAJE_PRUEBAS);

        messageActuator = new MessageActuator();
        messageActuator.controllers.add(conditionalController);
        messageActuator.message = MENSAJE_PRUEBAS;

        LogicBricksComponent lbc = new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addController(conditionalController)
                .addActuator(messageActuator)
                .build();


        entity.add(lbc);
        engine.update(1);

        assertTrue(checkMessage);


    }


}
package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.indignado.logicbricks.bricks.actuators.MessageActuator;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.systems.StateSystem;
import com.indignado.logicbricks.utils.logicbricks.LogicBricksBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Rubentxu.
 */
public class MessageActuatorSystemTest {
    private PooledEngine engine;
    private int statePrueba;
    private String name;
    private Entity entity;
    private MessageActuator messageActuator;

    private int MENSAJE_PRUEBAS = 1;
    private Boolean checkMessage = false;
    private LogicBricksBuilder logicBricksBuilder;


    @Before
    public void setup() {
        this.name = "BricksPruebas";
        this.statePrueba = 1;
        this.entity = new Entity();
        engine = new PooledEngine();
        engine.addSystem(new MessageActuatorSystem());
        engine.addSystem(new StateSystem());

        StateComponent stateComponent = new StateComponent();
        stateComponent.set(statePrueba);

        entity.add(stateComponent);
        engine.addEntity(entity);
        logicBricksBuilder = new LogicBricksBuilder(entity);

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

        logicBricksBuilder.addController(conditionalController, statePrueba)
                .addActuator(messageActuator, statePrueba)
                .connect(conditionalController);


        engine.update(1);

        assertTrue(checkMessage);


    }


}
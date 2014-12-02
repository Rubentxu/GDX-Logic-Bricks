package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.actuators.MessageActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.systems.StateSystem;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Rubentxu.
 */
public class MessageActuatorSystemTest {
    private PooledEngine engine;
    private String statePrueba;
    private String name;
    private Entity entity;
    private MessageActuator messageActuator;

    private int MENSAJE_PRUEBAS = 1;
    private Boolean checkMessage = false;
    private EntityBuilder entityBuilder;


    @Before
    public void setup() {
        this.name = "BricksPruebas";
        this.statePrueba = "StatePruebas";
        this.entity = new Entity();
        engine = new PooledEngine();
        engine.addSystem(new MessageActuatorSystem());
        engine.addSystem(new StateSystem(engine));

        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.getState(statePrueba));

        entity.add(stateComponent);
        engine.addEntity(entity);
        entityBuilder = new EntityBuilder(engine);

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

        entityBuilder.addController(conditionalController, statePrueba)
                .connectToActuator(messageActuator);


        engine.update(1);

        assertTrue(checkMessage);


    }


}
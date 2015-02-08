package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.bricks.base.BaseTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author Rubentxu
 */
public class StateSystemTest extends BaseTest {

    private StateComponent stateComponent;


    @Before
    public void setup() {
        game.getEngine().addSystem(new StateSystem());

        stateComponent =  entityBuilder.getComponent(StateComponent.class);
        stateComponent.changeCurrentState(PlayerState.WALKING.ordinal());
        Entity player = entityBuilder.getEntity();

    }


    @Test
    public void stateSystemTest() {

        float deltaTime = 1;
        engine.update(deltaTime);

        assertEquals(deltaTime, stateComponent.time, 0.1);

    }


}

package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.core.bricks.base.BaseTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author Rubentxu
 */
public class StateSystemTest extends BaseTest {

    LogicBricksEngine engine;
    private StateComponent stateComponent;


    @Before
    public void setup() {
        engine = new LogicBricksEngine();
        engine.addSystem(new StateSystem(null));

        stateComponent = new StateComponent();
        stateComponent.changeCurrentState(PlayerState.WALKING.ordinal());

    }


    @Test
    public void stateSystemTest() {
        Entity player = engine.createEntity();
        player.add(stateComponent);

        engine.addEntity(player);

        float deltaTime = 1;
        engine.update(deltaTime);

        assertEquals(deltaTime, stateComponent.time, 0.1);

    }


}

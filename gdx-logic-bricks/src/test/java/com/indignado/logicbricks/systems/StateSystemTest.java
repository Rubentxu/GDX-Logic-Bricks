package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.core.bricks.base.BaseTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created on 18/10/14.
 *
 * @author Rubentxu
 */
public class StateSystemTest extends BaseTest {

    PooledEngine engine;
    private StateComponent stateComponent;


    @Before
    public void setup() {
        engine = new PooledEngine();
        engine.addSystem(new StateSystem(new World(null, null, null, null)));

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

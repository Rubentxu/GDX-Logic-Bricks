package com.indignado.games.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.indignado.games.bricks.base.BaseTest;
import com.indignado.games.components.StateComponent;
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
        engine.addSystem(new StateSystem());

        stateComponent = new StateComponent();
        stateComponent.set(PlayerState.WALKING.name());

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

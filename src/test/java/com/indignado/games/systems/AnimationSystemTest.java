package com.indignado.games.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.indignado.games.bricks.base.BaseTest;
import com.indignado.games.components.AnimationComponent;
import com.indignado.games.components.StateComponent;
import com.indignado.games.components.TextureComponent;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Created on 18/10/14.
 *
 * @author Rubentxu
 */
public class AnimationSystemTest extends BaseTest {

    PooledEngine engine;
    TextureRegion playerTextureRegion;
    private AnimationComponent animationComponent;
    private StateComponent stateComponent;


    @BeforeClass
    public static void testSetup() {
        //GdxNativesLoader.load();
        // LwjglNativesLoader.load();


    }

    @Before
    public void setup() {
        engine = new PooledEngine();
        engine.addSystem(new AnimationSystem());

        playerTextureRegion = Mockito.mock(TextureRegion.class);
        animationComponent = new AnimationComponent();
        animationComponent.animations = getAnimations();

        stateComponent = new StateComponent();
        stateComponent.set(PlayerState.WALKING.ordinal());

    }


    @Test
    public void animationSystemTest() {

        Entity player = engine.createEntity();
        player.add(new TextureComponent(playerTextureRegion));
        player.add(stateComponent);
        player.add(animationComponent);

        engine.addEntity(player);

        float deltaTime = 1;
        engine.update(deltaTime);

        assertEquals(0, stateComponent.get());
        assertEquals(deltaTime, stateComponent.time, 0.1);
        assertNotEquals(playerTextureRegion, player.getComponent(TextureComponent.class).region);

    }


    @Test
    public void animationSystem2Test() {

        Entity player = engine.createEntity();
        player.add(new TextureComponent(playerTextureRegion));
        stateComponent.set(PlayerState.JUMP.ordinal());
        player.add(stateComponent);
        player.add(animationComponent);

        engine.addEntity(player);

        float deltaTime = 2;
        engine.update(deltaTime);

        assertEquals(1, stateComponent.get());

        engine.update(deltaTime);

        assertEquals(deltaTime * 2, stateComponent.time, 0.1);

    }


}
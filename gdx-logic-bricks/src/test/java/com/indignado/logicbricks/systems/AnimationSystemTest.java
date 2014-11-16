package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.indignado.logicbricks.bricks.base.BaseTest;
import com.indignado.logicbricks.components.ViewsComponent;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * Created on 18/10/14.
 *
 * @author Rubentxu
 */
public class AnimationSystemTest extends BaseTest {

    PooledEngine engine;
    TextureRegion playerTextureRegion;
    private ViewsComponent viewsComponent;


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


    }


    @Test
    public void animationSystemTest() {

        Entity player = engine.createEntity();
        // player.add(new TextureComponent(playerTextureRegion));
        // player.add(animateStateComponent);
        // player.add(animationComponent);

        engine.addEntity(player);

        float deltaTime = 1;
        engine.update(deltaTime);

        //assertEquals(0, animateStateComponent.getCurrentState());
        // assertEquals(deltaTime, animateStateComponent.time, 0.1);
        // assertNotEquals(playerTextureRegion, player.getComponent(TextureComponent.class).region);

    }


    @Test
    public void animationSystem2Test() {

       /* Entity player = engine.createEntity();
        player.add(new TextureComponent(playerTextureRegion));
        animateStateComponent.changeCurrentState(PlayerState.JUMP.ordinal());
        player.add(animateStateComponent);
        player.add(animationComponent);

        engine.addEntity(player);

        float deltaTime = 2;
        engine.update(deltaTime);

        assertEquals(1, animateStateComponent.getCurrentState());

        engine.update(deltaTime);

        assertEquals(deltaTime * 2, animateStateComponent.time, 0.1);
*/
    }


}

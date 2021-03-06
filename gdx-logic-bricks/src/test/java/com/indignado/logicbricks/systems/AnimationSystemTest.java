package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.core.bricks.base.BaseTest;
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

    TextureRegion playerTextureRegion;
    private ViewsComponent viewsComponent;


    @BeforeClass
    public static void testSetup() {
        //GdxNativesLoader.load();
        // LwjglNativesLoader.load();


    }

    @Before
    public void setup() {

        playerTextureRegion = Mockito.mock(TextureRegion.class);


    }


    @Test
    public void animationSystemTest() {

        Entity player = engine.createEntity();
        // player.addProperty(new TextureComponent(playerTextureRegion));
        // player.addProperty(animateStateComponent);
        // player.addProperty(animationComponent);

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
        player.addProperty(new TextureComponent(playerTextureRegion));
        animateStateComponent.changeCurrentState(PlayerState.JUMP.ordinal());
        player.addProperty(animateStateComponent);
        player.addProperty(animationComponent);

        engine.addEntity(player);

        float deltaTime = 2;
        engine.update(deltaTime);

        assertEquals(1, animateStateComponent.getCurrentState());

        engine.update(deltaTime);

        assertEquals(deltaTime * 2, animateStateComponent.time, 0.1);
*/
    }


}

package com.indignado.games.bricks.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.indignado.games.bricks.base.BaseTest;
import com.indignado.games.components.AnimationComponent;
import com.indignado.games.components.StateComponent;
import com.indignado.games.components.TextureComponent;
import com.indignado.games.systems.AnimationSystem;
import org.junit.Before;
import org.junit.Test;

/**
 * Created on 18/10/14.
 *
 * @author Rubentxu
 */
public class AnimationSystemTest extends BaseTest{

    PooledEngine engine;
    Texture playerTexture;
    private AnimationComponent animationComponent;
    private StateComponent stateComponent;



    @Before
    public void setup() {
        engine = new PooledEngine();
        engine.addSystem(new AnimationSystem());
        playerTexture = new Texture("player.png");
        animationComponent = new AnimationComponent();
        animationComponent.animations= getAnimations();

        stateComponent = new StateComponent();
        stateComponent.set(PlayerState.WALKING.ordinal());

    }


    @Test
    public void animationSystemTest(){
        Entity player = engine.createEntity();
        player.add(new TextureComponent(new TextureRegion(playerTexture)));
        player.add(stateComponent);
        player.add(animationComponent);

    }

}

package com.indignado.functional.test;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.physics.box2d.Body;
import com.indignado.functional.test.base.LogicBricksTest;
import com.indignado.functional.test.levels.simpleplatform.SimplePlatformLevel;
import com.indignado.logicbricks.utils.builders.BodyBuilder;


/**
 * @author Rubentxu.
 */
public class SimplePlatformTest extends LogicBricksTest {
    private BodyBuilder bodyBuilder;
    private Body ground;
    private Animation walking;
    private Animation idle;
    private Animation jump;
    private Animation fall;
    private ParticleEffect dustEffect;


    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;

        new LwjglApplication(new SimplePlatformTest(), config);

    }


    @Override
    public void create() {
        super.create();
        addLevel(new SimplePlatformLevel(world));


    }

}

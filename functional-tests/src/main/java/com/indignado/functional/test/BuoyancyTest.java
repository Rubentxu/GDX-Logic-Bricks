package com.indignado.functional.test;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.indignado.functional.test.base.LogicBricksTest;
import com.indignado.functional.test.levels.buoyancy.BuoyancyLevel;


/**
 * @author Rubentxu.
 */
public class BuoyancyTest extends LogicBricksTest {
    private String tag = this.getClass().getSimpleName();


    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        new LwjglApplication(new BuoyancyTest(), config);

    }


    @Override
    public void create() {
        super.create();
        addLevel(new BuoyancyLevel(game));

    }


}

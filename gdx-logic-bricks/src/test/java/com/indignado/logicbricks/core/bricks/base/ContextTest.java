package com.indignado.logicbricks.core.bricks.base;

import box2dLight.RayHandler;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.indignado.logicbricks.config.GameContext;
import org.mockito.Mockito;

/**
 * @author Rubentxu.
 */
public class ContextTest extends GameContext{

    @Override
    public AssetManager provideAssetManager() {
        return new AssetManager(new TestFileHandleResolver());

    }


    @Override
    public Batch provideBatch() {
        return Mockito.mock(SpriteBatch.class);

    }


    @Override
    public <T> RayHandler provideRayHandler(T physics) {
        return Mockito.mock(RayHandler.class);

    }


}

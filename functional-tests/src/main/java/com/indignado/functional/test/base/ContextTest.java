package com.indignado.functional.test.base;

import com.badlogic.gdx.assets.AssetManager;
import com.indignado.logicbricks.config.GameContext;

/**
 * @author Rubentxu.
 */
public class ContextTest extends GameContext{

    @Override
    public AssetManager provideAssetManager() {
        return new AssetManager(new TestFileHandleResolver());

    }

}

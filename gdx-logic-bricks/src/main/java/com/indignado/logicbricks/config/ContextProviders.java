package com.indignado.logicbricks.config;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.utils.builders.LBBuilders;

/**
 * @author Rubentxu.
 */
public interface ContextProviders {

    public Game provideGame(LogicBricksEngine engine, World physics);

    public AssetManager provideAssetManager();

    public <T> T providePhysics();

    public LogicBricksEngine provideEngine();

    public Camera provideCamera();

    public Batch provideBatch();

    public <T> LBBuilders provideUtilBuilder(LogicBricksEngine engine, T physics);

    void registerDefaultClasses();

}

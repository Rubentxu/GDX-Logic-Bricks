package com.indignado.logicbricks.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.indignado.logicbricks.utils.box2d.BodyBuilder;


/**
 * @author Rubentxu.
 */
public class World implements Disposable {
    private final LogicBricksEngine engine;
    private final AssetManager assetManager;
    private final EntityBuilder entityBuilder;
    private final BodyBuilder bodyBuilder;
    private final com.badlogic.gdx.physics.box2d.World physics;


    public World(com.badlogic.gdx.physics.box2d.World physics, AssetManager assetManager) {
        this.physics = physics;
        this.engine = new LogicBricksEngine();
        this.assetManager = assetManager;
        this.entityBuilder = new EntityBuilder(engine);
        this.bodyBuilder = new BodyBuilder(physics);

    }


    public void addEntityCreator(String nameEntity, EntityCreator creator) {
        engine.addEntityCreator(nameEntity, creator);

    }


    public <C extends EntityCreator> void createEntity(String nameEntity, Vector2 position, float angle) {
        EntityCreator creator = engine.getEntityCreator(nameEntity);
        engine.addEntity(creator.createEntity(this, position, angle));

    }


    public EntityBuilder getEntityBuilder() {
        return entityBuilder;

    }


    public BodyBuilder getBodyBuilder() {
        return bodyBuilder;

    }


    public AssetManager getAssetManager() {
        return assetManager;

    }


    public void update(float delta) {

    }


    public void resize(int width, int height) {

    }


    public void show() {

    }


    public void hide() {

    }


    public void pause() {

    }


    public void resume() {

    }


    @Override
    public void dispose() {

    }

}

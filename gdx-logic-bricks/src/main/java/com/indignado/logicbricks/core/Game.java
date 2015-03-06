package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.config.Settings;
import com.indignado.logicbricks.systems.DraggableBodySystem;
import com.indignado.logicbricks.systems.LogicBrickSystem;
import com.indignado.logicbricks.systems.RenderingSystem;

import java.util.Iterator;


/**
 * @author Rubentxu.
 */
public class Game implements Disposable, ContactListener {
    private static String tag = Game.class.getSimpleName();
    private static int levelIndex = 0;
    private final IntMap<LevelFactory> levelFactories;
    private final World physics;
    private final LogicBricksEngine engine;
    private final RenderingSystem renderingSystem;


    private CategoryBitsManager categoryBitsManager;

    private float fixedTimesStepAccumulator = 0.0f;
    private float fixedTimesStepAccumulatorRatio = 0.0f;
    private int nSteps;
    private int nStepsClamped;
    public StateMachine<Game> gameStateMachine;


    public Game(LogicBricksEngine engine, World physics) {
        this.engine = engine;
        this.physics = physics;
        renderingSystem = new RenderingSystem();
        renderingSystem.setProcessing(false);
        engine.addSystem(renderingSystem);

        levelFactories = new IntMap<LevelFactory>();
        categoryBitsManager = new CategoryBitsManager();

        //engine.update(0);

        Gdx.input.setInputProcessor(engine.getInputs());
        physics.setContactListener(this);
        Gdx.app.setLogLevel(Settings.DEBUG_LEVEL);

    }




    public void addLevelCreator(LevelFactory level) {
        levelIndex++;
        levelFactories.put(levelIndex, level);

    }


    public int getLevelsSize() {
        return levelIndex;

    }


    public void createLevel(int levelNumber) {
        engine.removeAllEntities();
        LevelFactory level = levelFactories.get(levelNumber);
        if (level != null) {
            level.loadAssets();
            level.createLevel();

        }
        if (Settings.DRAGGABLE_BOX2D_BODIES) engine.addSystem(new DraggableBodySystem());

    }





    public void update() {
        update(Gdx.graphics.getDeltaTime());

    }


    public void update(float dt) {
        fixedTimesStepAccumulator += dt;
        nSteps = MathUtils.round(fixedTimesStepAccumulator / Settings.FIXED_TIME_STEP);
        if(nSteps > 0) fixedTimesStepAccumulator -= nSteps * Settings.FIXED_TIME_STEP;
        fixedTimesStepAccumulatorRatio = fixedTimesStepAccumulator / Settings.FIXED_TIME_STEP;

        nStepsClamped = Math.min(nSteps, Settings.MAX_STEPS);

        for(int i = 0; i < nStepsClamped; ++i) {
            singleStep(Settings.FIXED_TIME_STEP);

        }
        renderingSystem.update(dt);

    }


    public void singleStep(float dt) {
        physics.step(dt, Settings.BOX2D_VELOCITY_ITERATIONS, Settings.BOX2D_POSITION_ITERATIONS);
        engine.update(dt);
        MessageManager.getInstance().update(dt);

    }


    private void activeLogicBrickSystemProcessing(boolean active) {
        Iterator<EntitySystem> it = engine.getSystems().iterator();
        while (it.hasNext()) {
            EntitySystem system = it.next();
            if (system instanceof LogicBrickSystem) system.setProcessing(active);
        }

    }


 /*   public void resize(int width, int height) {
        camera.viewportHeight = Settings.HEIGHT;
        camera.viewportWidth = (Settings.HEIGHT / height) * width;

    }


    public void show() {

    }


    public void hide() {

    }*/


    public void pause() {
        for (EntitySystem system : engine.getSystems()) {
            system.setProcessing(false);
        }
    }


    public void resume() {
        for (EntitySystem system : engine.getSystems()) {
            system.setProcessing(true);
        }
    }


    @Override
    public void dispose() {

    }


    public CategoryBitsManager getCategoryBitsManager() {
        return categoryBitsManager;

    }



    @Override
    public void beginContact(Contact contact) {
        for (ContactListener contactListener : engine.getContactSystems()) {
            contactListener.beginContact(contact);

        }

    }


    @Override
    public void endContact(Contact contact) {
        for (ContactListener contactListener : engine.getContactSystems()) {
            contactListener.endContact(contact);

        }

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        for (ContactListener contactListener : engine.getContactSystems()) {
            contactListener.preSolve(contact, oldManifold);

        }

    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        for (ContactListener contactListener : engine.getContactSystems()) {
            contactListener.postSolve(contact, impulse);

        }

    }

}

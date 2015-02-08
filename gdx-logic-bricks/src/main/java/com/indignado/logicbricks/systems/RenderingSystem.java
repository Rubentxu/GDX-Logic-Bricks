package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.data.ParticleEffectView;
import com.indignado.logicbricks.core.data.TextureView;
import com.indignado.logicbricks.core.data.View;
import com.indignado.logicbricks.utils.Log;

import java.util.Comparator;

/**
 * @author Rubentxu
 */
public class RenderingSystem extends LogicBrickSystem {
    private World physics;
    protected Viewport viewport;
    protected OrthographicCamera uiCamera;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Array<View> renderQueue;
    private Comparator<View> comparator;
    private ComponentMapper<ViewsComponent> vm;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    // Debug
    private ShapeRenderer debugShapeRenderer;
    private Box2DDebugRenderer debugRenderer;
    private BitmapFont debugFont;



    public RenderingSystem() {
        super(Family.all(ViewsComponent.class).get(), 5);
        vm = ComponentMapper.getFor(ViewsComponent.class);

        renderQueue = new Array<View>();
        comparator = new Comparator<View>() {
            @Override
            public int compare(View viewA, View viewB) {
                return (int) Math.signum(viewA.layer - viewB.layer);
            }
        };


        if (Settings.debug) {
            this.debugShapeRenderer = new ShapeRenderer();
            this.debugRenderer = new Box2DDebugRenderer(Settings.drawBodies, Settings.drawJoints, Settings.drawABBs,
                    Settings.drawInactiveBodies, Settings.drawVelocities, Settings.drawContacts);
            debugFont = new BitmapFont();
            debugFont.setUseIntegerPositions(false);
            debugFont.setScale(0.1f);
            uiCamera = new OrthographicCamera();

        }
        debugRenderer.setDrawAABBs(Settings.drawABBs);
        debugRenderer.setDrawBodies(Settings.drawBodies);
        debugRenderer.setDrawContacts(Settings.drawContacts);
        debugRenderer.setDrawInactiveBodies(Settings.drawInactiveBodies);
        debugRenderer.setDrawJoints(Settings.drawJoints);
        debugRenderer.setDrawVelocities(Settings.drawVelocities);

    }


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        batch = game.getBatch();
        camera = game.getCamera();
        camera.position.set(Settings.Width / 2, Settings.Height / 2, 0);
        physics = game.getPhysics();

    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        renderQueue.sort(comparator);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (View view : renderQueue) {
            if (view.tint != null) {
                batch.setColor(view.tint);
            } else {
                batch.setColor(Color.WHITE);
            }
            batch.getColor().a = view.opacity;

            if (view instanceof ParticleEffectView) {

                ParticleEffect effect = ((ParticleEffectView) view).effect;
                effect.setPosition(view.position.x, view.position.y);
                effect.update(deltaTime);
                if (((ParticleEffectView) view).autoStart) {
                    effect.start();
                    ((ParticleEffectView) view).autoStart = false;
                }
                effect.draw(batch);

            } else if (TextureView.class.isAssignableFrom(view.getClass())) {
                TextureView textureView = (TextureView) view;

                float originX = textureView.width * 0.5f;
                float originY = textureView.height * 0.5f;

                processTextureFlip(textureView);

                batch.draw(textureView.textureRegion, textureView.position.x - originX, textureView.position.y - originY,
                        originX, originY, textureView.width, textureView.height, 1, 1, textureView.rotation);

                // Gdx.app.log("RederingSystem", "texture width " + textureView.width + " height " + textureView.height
                //        + " texture position " + textureView.position);
            }


        }
        debugDrawUI();
        batch.end();
        renderQueue.clear();

        debugDrawWorld();


    }


    protected void debugDrawWorld() {
        if (Settings.debug) {

            debugShapeRenderer.setProjectionMatrix(camera.combined);

            if (Settings.drawGrid) {
                // Debug shapes
                debugShapeRenderer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
                debugShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                debugShapeRenderer.line(-Settings.Width * 0.5f, 0.0f, Settings.Width * 0.5f, 0.0f);
                debugShapeRenderer.line(0.0f, -Settings.Height * 0.5f, 0.0f, Settings.Height * 0.5f);

                debugShapeRenderer.setColor(0.0f, 1.0f, 0.0f, 1.0f);

                for (int i = -100; i <= 100; ++i) {
                    if (i == 0)
                        continue;

                    debugShapeRenderer.line(-Settings.Width * 0.5f, i, Settings.Width * 0.5f, i);
                }

                for (int i = -100; i <= 100; ++i) {
                    if (i == 0)
                        continue;

                    debugShapeRenderer.line(i, -Settings.Height * 0.5f, i, Settings.Height * 0.5f);
                }

                debugShapeRenderer.end();
            }


            debugRenderer.render(physics, camera.combined);

        }
    }

    protected void debugDrawUI() {
        if (Settings.debug) {
            if (Settings.drawFPS) {
                String fpsText = String.format("%d FPS", Gdx.graphics.getFramesPerSecond());
                debugFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                debugFont.draw(batch, fpsText, Settings.drawFPSPosX, Settings.drawFPSPosY);

            }
        }

    }


    private void processTextureFlip(TextureView view) {
        if ((view.flipX && !view.textureRegion.isFlipX()) || (!view.flipX && view.textureRegion.isFlipX())) {
            float temp = view.textureRegion.getU();
            view.textureRegion.setU(view.textureRegion.getU2());
            view.textureRegion.setU2(temp);
        }


        if ((view.flipY && !view.textureRegion.isFlipY()) || (!view.flipY && view.textureRegion.isFlipY())) {
            float temp = view.textureRegion.getV();
            view.textureRegion.setV(view.textureRegion.getV2());
            view.textureRegion.setV2(temp);

        }
    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.debugEntity != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        for (Object view : entity.getComponent(ViewsComponent.class).views) {
            renderQueue.add((View) view);
        }

    }


    public OrthographicCamera getCamera() {
        return camera;

    }

}

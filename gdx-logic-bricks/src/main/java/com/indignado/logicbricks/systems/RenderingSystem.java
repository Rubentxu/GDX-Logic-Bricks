package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.config.Settings;
import com.indignado.logicbricks.core.data.*;
import com.indignado.logicbricks.utils.Log;

import java.util.Comparator;

/**
 * @author Rubentxu
 */
public class RenderingSystem extends LogicBrickSystem {
    private World physics;
    protected Viewport viewport;
    protected OrthographicCamera uiCamera;
    private Batch batch;
    private Camera camera;
    private Array<View> renderQueue;
    private Comparator<View> comparator;
    private ComponentMapper<ViewsComponent> vm;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Matrix4 transformMatrix;

    // Debug
    private ShapeRenderer debugShapeRenderer;
    private Box2DDebugRenderer debugRenderer;
    private BitmapFont debugFont;


    public RenderingSystem() {
        super(Family.all(ViewsComponent.class).get(), 5);
        vm = ComponentMapper.getFor(ViewsComponent.class);
        transformMatrix = new Matrix4();
        setProcessing(false);
        renderQueue = new Array<View>();
        comparator = new Comparator<View>() {
            @Override
            public int compare(View viewA, View viewB) {
                return (int) Math.signum(viewA.layer - viewB.layer);
            }
        };


        if (Settings.DRAW_GRID) {
            this.debugShapeRenderer = new ShapeRenderer();
        }
        if (Settings.DEBUG && !Settings.TESTING) {
            this.debugRenderer = new Box2DDebugRenderer(Settings.DRAW_BOX2D_BODIES, Settings.DRAW_BOX2D_JOINTS, Settings.DRAW_BOX2D_ABBs,
                    Settings.DRAW_BOX2D_INACTIVE_BODIES, Settings.DRAW_BOX2D_VELOCITIES, Settings.DRAW_BOX2D_CONTACTS);
            debugRenderer.setDrawAABBs(Settings.DRAW_BOX2D_ABBs);
            debugRenderer.setDrawBodies(Settings.DRAW_BOX2D_BODIES);
            debugRenderer.setDrawContacts(Settings.DRAW_BOX2D_CONTACTS);
            debugRenderer.setDrawInactiveBodies(Settings.DRAW_BOX2D_INACTIVE_BODIES);
            debugRenderer.setDrawJoints(Settings.DRAW_BOX2D_JOINTS);
            debugRenderer.setDrawVelocities(Settings.DRAW_BOX2D_VELOCITIES);

            debugFont = new BitmapFont();
            debugFont.setUseIntegerPositions(false);
            debugFont.setScale(0.1f);
            uiCamera = new OrthographicCamera();

        }

    }


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        batch = context.provideBatch();
        camera = context.get(Camera.class);
        camera.position.set(Settings.WIDTH / 2, Settings.HEIGHT / 2, 0);
        physics = context.get(World.class);

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
                effect.update(deltaTime);
                if (((ParticleEffectView) view).autoStart) {
                    effect.start();
                    ((ParticleEffectView) view).autoStart = false;
                }
                effect.draw(batch);

            } else if (TextureView.class.isAssignableFrom(view.getClass())) {
                TextureView textureView = (TextureView) view;
                Transform2D transform = textureView.transform;
                if(textureView.textureRegion != null) {
                    processTextureFlip(textureView);
                    batch.draw(textureView.textureRegion, transform.bounds.x, transform.bounds.y, transform.pivot.x, transform.pivot.y,
                            transform.bounds.width, transform.bounds.height, 1, 1, transform.roll);

                }
            }

        }
        debugDrawUI();
        batch.end();
        renderQueue.clear();
        debugDrawWorld();


    }


    private Matrix4 getTransformMatrix(Transform transform) {
        transformMatrix.idt();
        transformMatrix.set(transform.x, transform.y, transform.z, transform.yaw, transform.pitch, transform.roll, 1,
                            transform.scaleX, transform.scaleY, transform.scaleZ);
        return transformMatrix;

    }


    protected void debugDrawWorld() {
        if (Settings.DEBUG && !Settings.TESTING) {
            if (Settings.DRAW_GRID) {
                // Debug shapes
                debugShapeRenderer.setProjectionMatrix(camera.combined);
                debugShapeRenderer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
                debugShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                debugShapeRenderer.line(-Settings.WIDTH * 0.5f, 0.0f, Settings.WIDTH * 0.5f, 0.0f);
                debugShapeRenderer.line(0.0f, -Settings.HEIGHT * 0.5f, 0.0f, Settings.HEIGHT * 0.5f);

                debugShapeRenderer.setColor(0.0f, 1.0f, 0.0f, 1.0f);

                for (int i = -100; i <= 100; ++i) {
                    if (i == 0)
                        continue;

                    debugShapeRenderer.line(-Settings.WIDTH * 0.5f, i, Settings.WIDTH * 0.5f, i);
                }

                for (int i = -100; i <= 100; ++i) {
                    if (i == 0)
                        continue;

                    debugShapeRenderer.line(i, -Settings.HEIGHT * 0.5f, i, Settings.HEIGHT * 0.5f);
                }

                debugShapeRenderer.end();
            }
            debugRenderer.render(physics, camera.combined);

        }
    }


    protected void debugDrawUI() {
        if (Settings.DEBUG && !Settings.TESTING) {
            if (Settings.DRAW_FPS) {
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
        if (Settings.DEBUG_ENTITY != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        for (Object view : entity.getComponent(ViewsComponent.class).views) {
            renderQueue.add((View) view);
        }

    }


    public Camera getCamera() {
        return camera;

    }

}

package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
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
import com.indignado.logicbricks.components.data.ParticleEffectView;
import com.indignado.logicbricks.components.data.TextureView;
import com.indignado.logicbricks.components.data.View;
import com.indignado.logicbricks.core.Settings;

import java.util.Comparator;

/**
 * @author Rubentxu
 */
public class RenderingSystem extends IteratingSystem {    private SpriteBatch batch;
    private OrthographicCamera camera;
    private final World physics;
    protected Viewport viewport;
    protected OrthographicCamera uiCamera;
    private Array<View> renderQueue;
    private Comparator<View> comparator;
    private ComponentMapper<ViewsComponent> vm;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    // Debug
    private ShapeRenderer debugShapeRenderer;
    private Box2DDebugRenderer debugRenderer;
    private BitmapFont debugFont;


    public RenderingSystem(SpriteBatch batch, OrthographicCamera camera, World physics) {
        super(Family.all(ViewsComponent.class).get(), 4);
        vm = ComponentMapper.getFor(ViewsComponent.class);

        renderQueue = new Array<View>();
        comparator = new Comparator<View>() {
            @Override
            public int compare(View viewA, View viewB) {
                return (int) Math.signum(viewA.layer - viewB.layer);
            }
        };

        this.batch = batch;
        this.camera = camera;
        this.camera.position.set(Settings.Width / 2, Settings.Height / 2, 0);
        this.physics = physics;

        if (Settings.debug) {
            this.debugShapeRenderer = new ShapeRenderer();
            this.debugRenderer = new Box2DDebugRenderer(Settings.drawBodies, Settings.drawJoints, Settings.drawABBs,
                    Settings.drawInactiveBodies, Settings.drawVelocities, Settings.drawContacts);
            this.debugFont = new BitmapFont();
            uiCamera = new OrthographicCamera();

        }

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
                effect.start();
                effect.draw(batch);

            } else if (TextureView.class.isAssignableFrom(view.getClass())) {
                TextureView textureView = (TextureView) view;
                if (textureView.textureRegion == null) {
                    continue;
                }

                float originX = textureView.width * 0.5f;
                float originY = textureView.height * 0.5f;

                processTextureFlip(textureView);

                batch.draw(textureView.textureRegion, textureView.position.x - originX, textureView.position.y - originY,
                        originX, originY, textureView.width, textureView.height, 1, 1, textureView.rotation);
            }


        }
        batch.end();
        renderQueue.clear();

        debugDrawWorld();
        debugDrawUI();

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

            debugRenderer.setDrawAABBs(Settings.drawABBs);
            debugRenderer.setDrawBodies(Settings.drawBodies);
            debugRenderer.setDrawContacts(Settings.drawContacts);
            debugRenderer.setDrawInactiveBodies(Settings.drawInactiveBodies);
            debugRenderer.setDrawJoints(Settings.drawJoints);
            debugRenderer.setDrawVelocities(Settings.drawVelocities);
            debugRenderer.render(physics, camera.combined);

        }
    }

    protected void debugDrawUI() {
        if (Settings.debug) {
            if (Settings.drawFPS) {
                String fpsText = String.format("%d FPS", Gdx.graphics.getFramesPerSecond());
                BitmapFont.TextBounds bounds = debugFont.getBounds(fpsText);
                batch.setProjectionMatrix(uiCamera.combined);
                batch.begin();
                debugFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                debugFont.draw(batch, fpsText, bounds.width - 20.0f, 20.0f);
                batch.end();
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
        for (View view : entity.getComponent(ViewsComponent.class).views) {
            renderQueue.add(view);
        }


    }


    public OrthographicCamera getCamera() {
        return camera;

    }

}

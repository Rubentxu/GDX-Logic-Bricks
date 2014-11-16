package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.data.ParticleEffectView;
import com.indignado.logicbricks.data.TextureView;
import com.indignado.logicbricks.data.View;

import java.util.Comparator;

/**
 * @author Rubentxu
 */
public class RenderingSystem extends IteratingSystem {

    public float WIDTH;
    public float HEIGHT;
    protected Viewport viewport;
    protected OrthographicCamera uiCamera;
    private SpriteBatch batch;
    private Array<View> renderQueue;
    private Comparator<View> comparator;
    private OrthographicCamera camera;
    private ComponentMapper<ViewsComponent> vm;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;


    public RenderingSystem(SpriteBatch batch, OrthographicCamera camera) {
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
        this.camera.position.set(WIDTH / 2, HEIGHT / 2, 0);

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
            Transform t = view.transform;

            if(view instanceof ParticleEffectView) {


            } else if(TextureView.class.isAssignableFrom(view.getClass())) {
                TextureView textureView = (TextureView) view;
                if (textureView.textureRegion == null) {
                    continue;
                }

                float originX = textureView.width * 0.5f;
                float originY = textureView.height * 0.5f;

                processTextureFlip(textureView);

                batch.draw(textureView.textureRegion, t.getPosition().x - originX, t.getPosition().y - originY, originX, originY,
                        textureView.width, textureView.height, 1, 1, MathUtils.radiansToDegrees * t.getRotation());
            }


        }
        batch.end();
        renderQueue.clear();

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

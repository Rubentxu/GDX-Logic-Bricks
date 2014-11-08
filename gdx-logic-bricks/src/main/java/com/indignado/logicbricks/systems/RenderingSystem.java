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


    public RenderingSystem(SpriteBatch batch) {
        super(Family.getFor(ViewsComponent.class),4);
        vm = ComponentMapper.getFor(ViewsComponent.class);

        renderQueue = new Array<View>();
        comparator = new Comparator<View>() {
            @Override
            public int compare(View viewA, View viewB) {
                return (int) Math.signum(viewA.layer - viewB.layer);
            }
        };
        this.batch = batch;
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(WIDTH / 2, HEIGHT / 2, 0);

    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        renderQueue.sort(comparator);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (View view : renderQueue) {

            if (view.textureRegion == null) {
                continue;
            }

            Transform t = view.transform;

            float originX = view.width * 0.5f;
            float originY = view.height * 0.5f;

            if (view.tint != null) {
                batch.setColor(view.tint);
            } else {
                batch.setColor(Color.WHITE);
            }

            batch.getColor().a = view.opacity;
            view.textureRegion.flip(view.flipX, view.flipY);

            batch.draw(view.textureRegion, t.getPosition().x - originX, t.getPosition().y - originY, originX, originY,
                    view.width, view.height, 1, 1, MathUtils.radiansToDegrees * t.getRotation());


        }
        batch.end();
        renderQueue.clear();

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

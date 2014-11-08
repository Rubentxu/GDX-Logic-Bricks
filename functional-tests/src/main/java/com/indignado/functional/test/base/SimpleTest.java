package com.indignado.functional.test.base;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.data.View;
import com.indignado.logicbricks.utils.box2d.BodyBuilder;;import java.io.File;
import java.net.URL;

/**
 * @author Rubentxu.
 */
public class SimpleTest extends LogicBricksTest {
    private BodyBuilder bodyBuilder;
    private World world;
    private Engine engine;


    public static void main(String[] args){
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;

        new LwjglApplication(new SimpleTest() , config);
    }


    public FileHandle getFileHandle(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        File file = new File(url.getPath());
        FileHandle fileHandle = new FileHandle(file);
        return fileHandle;

    }


    @Override
    protected void createWorld(World world, Engine engine) {
        this.world = world;
        this.engine = engine;
        bodyBuilder = new BodyBuilder(world);


        Body ground = bodyBuilder.fixture(bodyBuilder.fixtureDefBuilder()
                .boxShape(50, 1))
                .type(BodyDef.BodyType.StaticBody)
                .position(0, 0)
                .mass(1)
                .build();

        Entity player = new Entity();
        player.add(new StateComponent());

        Body bodyPlayer = bodyBuilder.fixture(bodyBuilder.fixtureDefBuilder()
                .boxShape(0.35f, 1))
                .type(BodyDef.BodyType.DynamicBody)
                .position(0, 30)
                .mass(1)
                .userData(player)
                .build();

        RigidBodiesComponents rbc = new RigidBodiesComponents();
        rbc.rigidBodies.add(bodyPlayer);
        player.add(rbc);


        Texture playerTexture = new Texture(getFileHandle("assets/textures/player.png"));

        View playerView = new View();
        playerView.height = 2;
        playerView.width = 1.5f;
        playerView.transform = bodyPlayer.getTransform();
        playerView.textureRegion = new TextureRegion(playerTexture);
        playerView.layer = 1;

        ViewsComponent viewsComponent =  new ViewsComponent();
        viewsComponent.views.add(playerView);

        player.add(viewsComponent);

        engine.addEntity(player);
    }
}

package com.indignado.functional.test;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.indignado.functional.test.base.LogicBricksTest;
import com.indignado.logicbricks.bricks.actuators.*;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;
import com.indignado.logicbricks.bricks.sensors.KeyboardSensor;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.data.Property;
import com.indignado.logicbricks.data.TextureView;
import com.indignado.logicbricks.utils.box2d.BodyBuilder;
import com.indignado.logicbricks.utils.box2d.FixtureDefBuilder;
import com.indignado.logicbricks.utils.logicbricks.LogicBricksBuilder;


/**
 * @author Rubentxu.
 */
public class FlyingDartTest extends LogicBricksTest {

    private Body ground;
    private ParticleEffect dustEffect;


    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;

        new LwjglApplication(new FlyingDartTest(), config);
    }


    @Override
    protected void createWorld(World world, Engine engine) {
        this.world = world;
        this.engine = engine;
        bodyBuilder = new BodyBuilder(world);
        camera.position.set(0,9,0);

        wall(15, 0, 1, 20);

        ground = bodyBuilder.fixture(bodyBuilder.fixtureDefBuilder()
                .boxShape(50, 1))
                .type(BodyDef.BodyType.StaticBody)
                .position(0, 0)
                .mass(1)
                .build();


        engine.addEntity(addArrow(new Vector2(-0, 10), -50));
        engine.addEntity(addArrow(new Vector2(-8, 10), -150));
        engine.addEntity(addArrow(new Vector2(-13, 2), 150));




    }


    private Entity addArrow(Vector2 position, float angle) {
        Entity arrow = new Entity();

        BlackBoardComponent context = new BlackBoardComponent();
        context.add(new Property<Boolean>("freeFlight", false));
        context.add(new Property<Boolean>("follow", true));
        arrow.add(context);

        StateComponent state = new StateComponent();
        state.createState("Default");
        arrow.add(state);

        Vector2[] vertices=new Vector2[4];
        vertices[0] = new Vector2(-1.5f,0);
        vertices[1] = new Vector2(0,-0.1f);
        vertices[2]= new Vector2(0.6f,0);
        vertices[3]= new Vector2(0,0.1f);

        Body bodyArrow = bodyBuilder.fixture(new FixtureDefBuilder()
                .polygonShape(vertices)
                .friction(0.5f)
                .restitution(0.5f))
                .type(BodyDef.BodyType.DynamicBody)
                .position(position.x, position.y)
                .bullet()
                .build();

        RigidBodiesComponents bodiesComponents = new RigidBodiesComponents();
        bodiesComponents.rigidBodies.add(bodyArrow);
        arrow.add(bodiesComponents);

        TextureView arrowView = new TextureView();
        arrowView.name = "Arrow";
        arrowView.textureRegion = new TextureRegion(new Texture(getFileHandle("assets/textures/dart.png")));
        arrowView.height = 0.4f;
        arrowView.width = 2.5f;
        arrowView.attachedTransform = bodyArrow.getTransform();
        arrowView.layer = 0;

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(arrowView);

        arrow.add(viewsComponent);

        KeyboardSensor initArrow = new KeyboardSensor();
        initArrow.keyCode = Input.Keys.A;

        ConditionalController arrowController = new ConditionalController();
        arrowController.type = ConditionalController.Type.AND;

        MotionActuator arrowMotion = new MotionActuator();
        arrowMotion.impulse = new Vector2((float)(20 * Math.cos(angle)),(float)(20 * Math.sin(angle)));
        arrowMotion.owner = arrow;

        LogicBricksBuilder logicBuilder = new LogicBricksBuilder(engine,arrow);
        logicBuilder.addController(arrowController,"Default")
                    .connectToSensor(initArrow)
                    .connectToActuator(arrowMotion);

        return arrow;

    }

}

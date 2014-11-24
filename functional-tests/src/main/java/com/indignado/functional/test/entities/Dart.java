package com.indignado.functional.test.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.core.EntityBuilder;
import com.indignado.logicbricks.core.actuators.MotionActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.KeyboardSensor;
import com.indignado.logicbricks.data.Property;
import com.indignado.logicbricks.data.TextureView;
import com.indignado.logicbricks.utils.box2d.BodyBuilder;
import com.indignado.logicbricks.utils.box2d.FixtureDefBuilder;

import java.io.File;
import java.net.URL;

/**
 * @author Rubentxu.
 */
public class Dart extends Entity {

    public Dart(Engine engine, World world, Vector2 position, float angle) {

        BlackBoardComponent context = new BlackBoardComponent();
        context.addProperty(new Property<String>("type", "arrow"));
        context.addProperty(new Property<Boolean>("freeFlight", false));
        context.addProperty(new Property<Boolean>("follow", true));
        this.add(context);

        StateComponent state = new StateComponent();
        state.createState("Default");
        this.add(state);

        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(-1.5f, 0);
        vertices[1] = new Vector2(0, -0.1f);
        vertices[2] = new Vector2(0.6f, 0);
        vertices[3] = new Vector2(0, 0.1f);

        Body bodyArrow = new BodyBuilder(world).fixture(new FixtureDefBuilder()
                .polygonShape(vertices)
                .friction(0.5f)
                .restitution(0.5f))
                .type(BodyDef.BodyType.DynamicBody)
                .position(position.x, position.y)
                .bullet()
                .userData(context)
                .build();

        RigidBodiesComponents bodiesComponents = new RigidBodiesComponents();
        bodiesComponents.rigidBodies.add(bodyArrow);
        this.add(bodiesComponents);

        TextureView arrowView = new TextureView();
        arrowView.setName("Arrow");
        arrowView.setTextureRegion(new TextureRegion(new Texture(getFileHandle("assets/textures/dart.png"))));
        arrowView.setHeight(0.4f);
        arrowView.setWidth(2.5f);
        arrowView.setAttachedTransform(bodyArrow.getTransform());
        arrowView.setLayer(0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(arrowView);

        this.add(viewsComponent);

        KeyboardSensor initArrow = new KeyboardSensor();
        initArrow.setKeyCode(Input.Keys.A);

        ConditionalController arrowController = new ConditionalController();
        arrowController.setType(ConditionalController.Type.AND);

        MotionActuator arrowMotion = new MotionActuator();
        arrowMotion.setImpulse(new Vector2((float) (20 * Math.cos(angle)), (float) (20 * Math.sin(angle))));
        arrowMotion.setOwner(this);

        EntityBuilder logicBuilder = new EntityBuilder(engine);
        logicBuilder.addController(logicBuilder.controller(ConditionalController.class)
                .setType(ConditionalController.Type.AND), "Default")
                .connectToSensor(logicBuilder.sensor(KeyboardSensor.class)
                        .setKeyCode(Input.Keys.A))
                .connectToActuator(logicBuilder.actuator(MotionActuator.class)
                        .setImpulse(new Vector2((float) (20 * Math.cos(angle)), (float) (20 * Math.sin(angle))))
                        .setOwner(this))
                .build(this);


    }


    protected FileHandle getFileHandle(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        File file = new File(url.getPath());
        FileHandle fileHandle = new FileHandle(file);
        return fileHandle;

    }

}

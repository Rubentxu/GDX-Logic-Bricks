package com.indignado.functional.test.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.components.data.TextureView;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.core.actuators.MotionActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.KeyboardSensor;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;
import com.indignado.logicbricks.utils.builders.actuators.MotionActuatorBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.KeyboardSensorBuilder;

/**
 * @author Rubentxu.
 */
public class Dart implements EntityFactory {
    private String dartTexture = "assets/textures/dart.png";


    @Override
    public void loadAssets(AssetManager manager) {
        if(! manager.isLoaded(dartTexture)) manager.load(dartTexture, Texture.class);

    }


    @Override
    public Entity createEntity(World world) {
        EntityBuilder entityBuilder = world.getEntityBuilder();
        BodyBuilder bodyBuilder = world.getBodyBuilder();

        BlackBoardComponent context = entityBuilder.getComponent(BlackBoardComponent.class);
        context.addProperty(new Property<String>("type", "arrow"));
        context.addProperty(new Property<Boolean>("freeFlight", false));
        context.addProperty(new Property<Boolean>("follow", true));


        StateComponent state = entityBuilder.getComponent(StateComponent.class);
        state.createState("Default");


        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(-1.5f, 0);
        vertices[1] = new Vector2(0, -0.1f);
        vertices[2] = new Vector2(0.6f, 0);
        vertices[3] = new Vector2(0, 0.1f);

        Body bodyArrow = bodyBuilder.fixture(new FixtureDefBuilder()
                .polygonShape(vertices)
                .friction(0.5f)
                .restitution(0.5f))
                .type(BodyDef.BodyType.DynamicBody)
                .position(0, 0)
                .bullet()
                .build();

        RigidBodiesComponents bodiesComponents = entityBuilder.getComponent(RigidBodiesComponents.class);
        bodiesComponents.rigidBodies.add(bodyArrow);


        TextureView arrowView = new TextureView();
        arrowView.setName("Arrow");

        arrowView.setTextureRegion(new TextureRegion(world.getAssetManager().get(dartTexture, Texture.class)));
        arrowView.setHeight(1f);
        arrowView.setWidth(2.5f);
        arrowView.setAttachedTransform(bodyArrow.getTransform());
        arrowView.setLayer(0);

        ViewsComponent viewsComponent = entityBuilder.getComponent(ViewsComponent.class);
        viewsComponent.views.add(arrowView);


        KeyboardSensor initArrow = BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                .setKeyCode(Input.Keys.A).getBrick();

        ConditionalController arrowController = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setType(ConditionalController.Type.AND).getBrick();

        MotionActuator motionActuator = BricksUtils.getBuilder(MotionActuatorBuilder.class)
                .setImpulse(new Vector2((float) (20 * Math.cos(0)), (float) (20 * Math.sin(0))))
                .getBrick();

        Entity entity = entityBuilder.addController(arrowController, "Default")
                .connectToSensor(initArrow)
                .connectToActuator(motionActuator)
                .build();
        Gdx.app.log("Dart","instance" + entity);
        return entity;
    }


}

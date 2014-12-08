package com.indignado.functional.test.levels.flyingDart.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.functional.test.levels.flyingDart.CalculateVelocityScript;
import com.indignado.logicbricks.components.*;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.components.data.TextureView;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.MessageManager;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.core.actuators.MotionActuator;
import com.indignado.logicbricks.core.controllers.ScriptController;
import com.indignado.logicbricks.core.sensors.MessageSensor;
import com.indignado.logicbricks.core.sensors.PropertySensor;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;
import com.indignado.logicbricks.utils.builders.actuators.MotionActuatorBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ScriptControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.MessageSensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.PropertySensorBuilder;

/**
 * @author Rubentxu.
 */
public class Dart extends EntityFactory {
    private String dartTexture = "assets/textures/dart.png";


    public Dart(World world) {
        super(world);
    }


    @Override
    public void loadAssets() {
        if(! world.getAssetManager().isLoaded(dartTexture)) world.getAssetManager().load(dartTexture, Texture.class);

    }


    @Override
    public Entity createEntity() {
        EntityBuilder entityBuilder = world.getEntityBuilder();
        entityBuilder.initialize();
        BodyBuilder bodyBuilder = world.getBodyBuilder();

        BlackBoardComponent context = entityBuilder.getComponent(BlackBoardComponent.class);
        context.addProperty(new Property<Boolean>("freeFlight", false));
        context.addProperty(new Property<Boolean>("follow", true));

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Dart";
        identity.category = world.getCategoryBitsManager().getCategoryBits("Dart");
        identity.collisionMask = (short) (world.getCategoryBitsManager().getCategoryBits("Wall") | world.getCategoryBitsManager().getCategoryBits("Ground"));

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
                .bullet()
                .build();

        RigidBodiesComponents bodiesComponents = entityBuilder.getComponent(RigidBodiesComponents.class);
        bodiesComponents.rigidBodies.add(bodyArrow);

        TextureView arrowView = new TextureView();
        arrowView.setName("Arrow");
        arrowView.setTextureRegion(new TextureRegion(world.getAssetManager().get(dartTexture, Texture.class)));
        arrowView.setHeight(1f);
        arrowView.setWidth(2.3f);
        arrowView.setAttachedTransform(bodyArrow.getTransform());
        arrowView.setLayer(0);

        ViewsComponent viewsComponent = entityBuilder.getComponent(ViewsComponent.class);
        viewsComponent.views.add(arrowView);



       /* PropertySensor freeFlight = BricksUtils.getBuilder(PropertySensorBuilder.class)
                                    .setEvaluationType(PropertySensor.EvaluationType.EQUAL)
                                    .setProperty("freeFlight")
                                    .setValue(false)
                                    .setOnce(true)
                                    .setName("DartSensor")
                                    .getBrick();

        MessageSensor messageSensor = BricksUtils.getBuilder(MessageSensorBuilder.class)
                .setMessageListen("PositionMessage")
                .setAutoRegister(false)
                .setOnce(true)
                .setName("PositionMessage")
                .getBrick();

        MessageDispatcher.getInstance().addListener(messageSensor, MessageManager.getMessageKey(messageSensor.messageListen));


        ScriptController calculateVelocityScript = BricksUtils.getBuilder(ScriptControllerBuilder.class)
                .setScript(new CalculateVelocityScript())
                .setName("CalculateVelocityScript")
                .getBrick();


        MotionActuator motionActuator = BricksUtils.getBuilder(MotionActuatorBuilder.class)
                .setTargetRigidBody(bodyArrow)
                .setImpulse(new Vector2(20,11))
                .setName("ActuatorFreeFlight")
                .getBrick();*/


        Entity entity = entityBuilder
               /* .addController(calculateVelocityScript, "Default")
                .connectToSensors(freeFlight, messageSensor)
                .connectToActuator(motionActuator)*/
                .getEntity();

        Gdx.app.log("Dart","size components " + entity.getComponents().size());
        return entity;
    }


}

package com.indignado.functional.test.levels.simpleplatform.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.components.*;
import com.indignado.logicbricks.components.data.AnimationView;
import com.indignado.logicbricks.components.data.ParticleEffectView;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.core.actuators.*;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.AlwaysSensor;
import com.indignado.logicbricks.core.sensors.CollisionSensor;
import com.indignado.logicbricks.core.sensors.KeyboardSensor;
import com.indignado.logicbricks.core.sensors.PropertySensor;
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.actuators.*;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.CollisionSensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.KeyboardSensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.PropertySensorBuilder;

/**
 * @author Rubentxu.
 */
public class PlayerPlatform implements EntityFactory {
    private String effect = "assets/particles/dust.pfx";
    private String atlas = "assets/animations/sprites.pack";


    @Override
    public void loadAssets(AssetManager manager) {
        if(! manager.isLoaded(effect)) manager.load(effect, ParticleEffect.class);
        if(! manager.isLoaded(atlas)) manager.load(atlas, TextureAtlas.class);

    }


    @Override
    public Entity createEntity(World world) {
        Entity player = world.getEngine().createEntity();
        OrthographicCamera camera = world.getCamera();
        EntityBuilder entityBuilder = world.getEntityBuilder();

        ParticleEffect dustEffect = world.getAssetManager().get(effect, ParticleEffect.class);

        TextureAtlas textureAtlas = world.getAssetManager().get(atlas,TextureAtlas.class);
        Array<TextureAtlas.AtlasRegion> heroWalking = textureAtlas.findRegions("Andando");
        Array<TextureAtlas.AtlasRegion> heroJump = textureAtlas.findRegions("Saltando");
        Array<TextureAtlas.AtlasRegion> heroFall = textureAtlas.findRegions("Cayendo");
        Array<TextureAtlas.AtlasRegion> heroIdle = textureAtlas.findRegions("Parado");

        Animation walking = new Animation(0.02f, heroWalking, Animation.PlayMode.LOOP);
        Animation jump = new Animation(0.02f * 7, heroJump, Animation.PlayMode.NORMAL);
        Animation fall = new Animation(0.02f * 5, heroFall, Animation.PlayMode.NORMAL);
        Animation idle = new Animation(0.02f * 4, heroIdle, Animation.PlayMode.LOOP);

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Player";

        StateComponent stateComponent = entityBuilder.getComponent(StateComponent.class);
        stateComponent.createState("Idle");
        stateComponent.createState("Walking");

        BlackBoardComponent blackBoardComponent = entityBuilder.getComponent(BlackBoardComponent.class);
        blackBoardComponent.addProperty(new Property<Boolean>("isGround", false));

        Body bodyPlayer = world.getBodyBuilder().fixture(world.getBodyBuilder().fixtureDefBuilder()
                .boxShape(0.35f, 1)
                .density(1))
                .type(BodyDef.BodyType.DynamicBody)
                .position(0, 5)
                .mass(1)
                .build();

        RigidBodiesComponents rbc = entityBuilder.getComponent(RigidBodiesComponents.class);
        rbc.rigidBodies.add(bodyPlayer);


        AnimationView playerView = new AnimationView();
        playerView.setHeight(2.3f);
        playerView.setWidth(1.8f);
        playerView.setAttachedTransform(bodyPlayer.getTransform());
        playerView.animations = new IntMap<>();
        playerView.animations.put(stateComponent.getState("Idle"), idle);
        playerView.animations.put(stateComponent.getState("Walking"), walking);
        playerView.setLayer(1);

        ParticleEffectView particleEffectView = new ParticleEffectView();
        particleEffectView.setAttachedTransform(bodyPlayer.getTransform());
        particleEffectView.effect = dustEffect;
        particleEffectView.setLocalPosition(new Vector2(0, -1));
        particleEffectView.setTint(Color.BLUE);

        ViewsComponent viewsComponent = entityBuilder.getComponent(ViewsComponent.class);
        viewsComponent.views.add(playerView);
        viewsComponent.views.add(particleEffectView);


        KeyboardSensor keyboardSensor = BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                .setKeyCode(Input.Keys.D)
                .getBrick();

        ConditionalController controller = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setType(ConditionalController.Type.AND)
                .getBrick();


        MotionActuator motionActuator = BricksUtils.getBuilder(MotionActuatorBuilder.class)
                                        .setImpulse(new Vector2(1, 0))
                                        .setLimitVelocityX(7)
                                        .getBrick();


        KeyboardSensor keyboardSensor2 =  BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                                        .setKeyCode(Input.Keys.A)
                                        .getBrick();

        ConditionalController controller2 = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setType(ConditionalController.Type.AND)
                .getBrick();

        MotionActuator motionActuator2 =  BricksUtils.getBuilder(MotionActuatorBuilder.class)
                                        .setImpulse(new Vector2(-1, 0))
                                        .setLimitVelocityX(7)
                                        .getBrick();


        ConditionalController controller3 = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setType(ConditionalController.Type.AND)
                .getBrick();


        CameraActuator cameraActuator =  BricksUtils.getBuilder(CameraActuatorBuilder.class)
                                        .setHeight((short) 1)
                                        .setCamera(camera)
                                        .getBrick();


        TextureActuator textureActuator = BricksUtils.getBuilder(TextureActuatorBuilder.class)
                                        .setFlipX(false)
                                        .setTextureView(playerView)
                                        .getBrick();


        StateActuator stateActuator = new StateActuator();
        
        stateActuator.setState(1);

        StateActuator stateActuatorB = new StateActuator();        
        stateActuatorB.setState(1);

        StateActuator stateActuator2 = new StateActuator();
        
        stateActuator2.setState(0);

        

        entityBuilder.addController(controller, "Idle", "Walking")
                .connectToSensor(keyboardSensor)
                .connectToActuator(motionActuator)
                .connectToActuator(textureActuator)
                .connectToActuator(stateActuator);


        TextureActuator textureActuator2 = BricksUtils.getBuilder(TextureActuatorBuilder.class)
                                        .setFlipX(true)
                                        .setTextureView(playerView)
                                        .getBrick();

        entityBuilder.addController(controller2, "Idle", "Walking")
                .connectToSensor(keyboardSensor2)
                .connectToActuator(motionActuator2)
                .connectToActuator(textureActuator2)
                .connectToActuator(stateActuatorB);


        AlwaysSensor alwaysSensor = new AlwaysSensor();
        entityBuilder.addController(controller3, "Idle", "Walking")
                .connectToSensor(alwaysSensor)
                .connectToActuator(cameraActuator);


        ConditionalController controller4 = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                                          .setType(ConditionalController.Type.NOR)
                                          .getBrick();

        EditRigidBodyActuator editRigidBodyActuator1 =  BricksUtils.getBuilder(EditRigidBodyActuatorBuilder.class)
                                                    .setFriction(40)
                                                    .setTargetRigidBody(bodyPlayer)
                                                    .getBrick();

        entityBuilder.addController(controller4, "Idle", "Walking")
                .connectToSensor(keyboardSensor)
                .connectToSensor(keyboardSensor2)
                .connectToActuator(editRigidBodyActuator1)
                .connectToActuator(stateActuator2);


        ConditionalController controller5 = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                                          .setType(ConditionalController.Type.OR)
                                          .getBrick();

        EditRigidBodyActuator editRigidBodyActuator2 = BricksUtils.getBuilder(EditRigidBodyActuatorBuilder.class)
                                                    .setFriction(0.3f)
                                                    .setTargetRigidBody(bodyPlayer)
                                                    .getBrick()   ;

        entityBuilder.addController(controller5, "Idle", "Walking")
                .connectToSensor(keyboardSensor)
                .connectToSensor(keyboardSensor2)
                .connectToActuator(editRigidBodyActuator2);


        CollisionSensor collisionSensor = BricksUtils.getBuilder(CollisionSensorBuilder.class)
                                        .setTargetName("Ground")
                                        .getBrick();

        ConditionalController controllerGround = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                                            .setType(ConditionalController.Type.AND)
                                            .getBrick();

        ConditionalController controllerNotGround = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                                                .setType(ConditionalController.Type.NOR)
                                                .getBrick();

        PropertyActuator<Boolean> propertyActuator = BricksUtils.getBuilder(PropertyActuatorBuilder.class)
                                                .setProperty("isGround")
                                                .setValue(true)
                                                .setMode(PropertyActuator.Mode.Assign)
                                                .getBrick();


        PropertyActuator<Boolean> propertyActuator2 = BricksUtils.getBuilder(PropertyActuatorBuilder.class)
        .setProperty("isGround")
        .setValue(false)
        .setMode(PropertyActuator.Mode.Assign)
                .getBrick();


        entityBuilder.addController(controllerGround, "Idle", "Walking")
                .connectToSensor(collisionSensor)
                .connectToActuator(propertyActuator);


        entityBuilder.addController(controllerNotGround, "Idle", "Walking")
                .connectToSensor(collisionSensor)
                .connectToActuator(propertyActuator2);


        KeyboardSensor keySensorJump = BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                                    .setKeyCode(Input.Keys.W)
                                    .getBrick();

        PropertySensor propertySensorIsGround = BricksUtils.getBuilder(PropertySensorBuilder.class)
                                            .setProperty("isGround")
                                            .setEvaluationType(PropertySensor.EvaluationType.EQUAL)
                                            .setValue(true)
                                            .getBrick();

        ConditionalController controllerJump = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                                            .setType(ConditionalController.Type.AND)
                                            .getBrick();


        MotionActuator motionActuatorJump = BricksUtils.getBuilder(MotionActuatorBuilder.class)
                                        .setImpulse(new Vector2(0, 3))
                                        .setLimitVelocityY(7)
                                        .getBrick();


        entityBuilder.addController(controllerJump, "Idle", "Walking")
                .connectToSensor(keySensorJump)
                .connectToSensor(propertySensorIsGround)
                .connectToActuator(motionActuatorJump);

        return entityBuilder.build(player);

    }

}

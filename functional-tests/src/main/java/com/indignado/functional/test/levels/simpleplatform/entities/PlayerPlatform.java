package com.indignado.functional.test.levels.simpleplatform.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
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
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.core.actuators.*;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.data.AnimationView;
import com.indignado.logicbricks.core.data.ParticleEffectView;
import com.indignado.logicbricks.core.data.Property;
import com.indignado.logicbricks.core.sensors.*;
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.actuators.*;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.AlwaysSensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.CollisionSensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.DelaySensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.KeyboardSensorBuilder;

/**
 * @author Rubentxu.
 */
public class PlayerPlatform extends EntityFactory {
    private String effect = "assets/particles/dust.pfx";
    private String atlas = "assets/animations/sprites.pack";


    public PlayerPlatform(World world) {
        super(world);
    }


    @Override
    public void loadAssets() {
        if (!world.getAssetManager().isLoaded(effect)) world.getAssetManager().load(effect, ParticleEffect.class);
        if (!world.getAssetManager().isLoaded(atlas)) world.getAssetManager().load(atlas, TextureAtlas.class);

    }


    @Override
    public Entity createEntity() {
        OrthographicCamera camera = world.getCamera();
        EntityBuilder entityBuilder = world.getEntityBuilder();
        entityBuilder.initialize();

        // Assets entity
        ParticleEffect dustEffect = world.getAssetManager().get(effect, ParticleEffect.class);
        TextureAtlas textureAtlas = world.getAssetManager().get(atlas, TextureAtlas.class);
        Array<TextureAtlas.AtlasRegion> heroWalking = textureAtlas.findRegions("Andando");
        Array<TextureAtlas.AtlasRegion> heroJump = textureAtlas.findRegions("Saltando");
        Array<TextureAtlas.AtlasRegion> heroFall = textureAtlas.findRegions("Cayendo");
        Array<TextureAtlas.AtlasRegion> heroIdle = textureAtlas.findRegions("Parado");

        Animation walking = new Animation(0.02f, heroWalking, Animation.PlayMode.LOOP);
        Animation jump = new Animation(0.02f * 7, heroJump, Animation.PlayMode.NORMAL);
        Animation fall = new Animation(0.02f * 5, heroFall, Animation.PlayMode.NORMAL);
        Animation idle = new Animation(0.02f * 4, heroIdle, Animation.PlayMode.LOOP);

        // Create General Components
        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Player";

        StateComponent stateComponent = entityBuilder.getComponent(StateComponent.class);
        stateComponent.createState("Idle");
        stateComponent.createState("Walking");
        stateComponent.createState("Jump");
        stateComponent.createState("Fall");

        BlackBoardComponent blackBoardComponent = entityBuilder.getComponent(BlackBoardComponent.class);
        blackBoardComponent.addProperty(new Property<Boolean>("isGround", false));

        Body bodyPlayer = world.getBodyBuilder().fixture(world.getBodyBuilder().fixtureDefBuilder()
                .boxShape(0.35f, 1)
                .density(1))
                .type(BodyDef.BodyType.DynamicBody)
                .fixedRotation()
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
        playerView.animations.put(stateComponent.getState("Jump"), jump);
        playerView.animations.put(stateComponent.getState("Fall"), fall);
        playerView.setLayer(1);

        ParticleEffectView particleEffectView = new ParticleEffectView();
        particleEffectView.setAttachedTransform(bodyPlayer.getTransform());
        particleEffectView.effect = dustEffect;
        particleEffectView.setLocalPosition(new Vector2(0, -1));

        ViewsComponent viewsComponent = entityBuilder.getComponent(ViewsComponent.class);
        viewsComponent.views.add(playerView);
        viewsComponent.views.add(particleEffectView);


        // ALL States
        // Create Bricks Camera ----------------------------------------------------------------
        AlwaysSensor alwaysSensorCamera = BricksUtils.getBuilder(AlwaysSensorBuilder.class)
                .setPulse(Sensor.Pulse.PM_TRUE)
                .setName("alwaysSensorCamera")
                .getBrick();

        ConditionalController controllerCamera = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .setName("controllerCamera")
                .getBrick();

        CameraActuator cameraActuator = BricksUtils.getBuilder(CameraActuatorBuilder.class)
                .setHeight((short) 1)
                .setCamera(camera)
                .setName("cameraActuator")
                .getBrick();

        entityBuilder.addController(controllerCamera, "Idle", "Walking", "Jump", "Fall")
                .connectToSensor(alwaysSensorCamera)
                .connectToActuator(cameraActuator);

        // Collision Sensor ----------------------------------------------------------------
        CollisionSensor collisionSensorGround = BricksUtils.getBuilder(CollisionSensorBuilder.class)
                .setTargetName("Ground")
                .setPulse(Sensor.Pulse.PM_TRUE, Sensor.Pulse.PM_FALSE)
                .setName("collisionSensorGround")
                .getBrick();


        /* State Walking to Change State Idle ----------------------------------------------------------------
           ---------------------------------------------------------------------------------- */
        KeyboardSensor keyboardSensorWalkingRight = BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                .setKeyCode(Input.Keys.D)
                .setPulse(Sensor.Pulse.PM_TRUE, Sensor.Pulse.PM_FALSE)
                .setName("keyboardSensorWalkingRight")
                .getBrick();

        KeyboardSensor keyboardSensorWalkingLeft = BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                .setKeyCode(Input.Keys.A)
                .setPulse(Sensor.Pulse.PM_TRUE, Sensor.Pulse.PM_FALSE)
                .setName("keyboardSensorWalkingLeft")
                .getBrick();

        ConditionalController controllerMoveFalse = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_NOR)
                .setName("controllerChangeStateIdle")
                .getBrick();

        StateActuator stateActuatorIdle = BricksUtils.getBuilder(StateActuatorBuilder.class)
                .setChangeState(stateComponent.getState("Idle"))
                .setName("stateActuatorIdle")
                .getBrick();

        entityBuilder.addController(controllerMoveFalse, "Walking")
                .connectToSensors(keyboardSensorWalkingRight, keyboardSensorWalkingLeft)
                .connectToActuators(stateActuatorIdle);

        // Ground contact
        ConditionalController controllerGround = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .setName("controllerGround")
                .getBrick();

        entityBuilder.addController(controllerGround, "Walking")
                .connectToSensor(collisionSensorGround)
                .connectToActuator(stateActuatorIdle);


         /* State Idle to Change State Walking ----------------------------------------------------------------
           ---------------------------------------------------------------------------------- */
        KeyboardSensor keyboardSensorWalkingRight2 = BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                .setKeyCode(Input.Keys.D)
                .setPulse(Sensor.Pulse.PM_TRUE, Sensor.Pulse.PM_FALSE)
                .setName("keyboardSensorWalkingRight2")
                .getBrick();

        KeyboardSensor keyboardSensorWalkingLeft2 = BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                .setKeyCode(Input.Keys.A)
                .setPulse(Sensor.Pulse.PM_TRUE, Sensor.Pulse.PM_FALSE)
                .setName("keyboardSensorWalkingLeft2")
                .getBrick();

        ConditionalController controllerChangeStateWalking = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_OR)
                .setName("controllerChangeStateWalking")
                .getBrick();

        StateActuator stateActuatorWalking = BricksUtils.getBuilder(StateActuatorBuilder.class)
                .setChangeState(stateComponent.getState("Walking"))
                .setName("stateActuatorWalking")
                .getBrick();

        entityBuilder.addController(controllerChangeStateWalking, "Idle")
                .connectToSensors(keyboardSensorWalkingRight2, keyboardSensorWalkingLeft2)
                .connectToActuators(stateActuatorWalking);


         /* Idle/Walking to Change State Jump ----------------------------------------------------------------
           ---------------------------------------------------------------------------------- */
        KeyboardSensor keyboardSensorJump = BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                .setKeyCode(Input.Keys.W)
                .setPulse(Sensor.Pulse.PM_TRUE)
                .setName("keyboardSensorJump")
                .getBrick();

        ConditionalController controllerJump = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .setName("controllerJump")
                .getBrick();

        StateActuator stateActuatorJump = BricksUtils.getBuilder(StateActuatorBuilder.class)
                .setChangeState(stateComponent.getState("Jump"))
                .setName("stateActuatorJump")
                .getBrick();

        entityBuilder.addController(controllerJump, "Idle", "Walking")
                .connectToSensor(keyboardSensorJump)
                .connectToActuator(stateActuatorJump);

        /* State Jump/Fall to Change State Idle ----------------------------------------------------------------
           ---------------------------------------------------------------------------------- */
        DelaySensor delaySensorContactGround = BricksUtils.getBuilder(DelaySensorBuilder.class)
                .setDelay(0.4f)
                .setDuration(3f)
                .setPulse(Sensor.Pulse.PM_TRUE)
                .getBrick();

        ConditionalController controllerGround2 = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .setName("controllerGround2")
                .getBrick();

        StateActuator stateActuatorIdle2 = BricksUtils.getBuilder(StateActuatorBuilder.class)
                .setChangeState(stateComponent.getState("Idle"))
                .setName("stateActuatorIdle2")
                .getBrick();


        entityBuilder.addController(controllerGround2, "Jump", "Fall")
                .connectToSensors(collisionSensorGround, delaySensorContactGround)
                .connectToActuator(stateActuatorIdle2);

        /* State Idle ----------------------------------------------------------------
           ---------------------------------------------------------------------------------- */

        // ** Idle Friction **
        AlwaysSensor alwaysSensorIdle = BricksUtils.getBuilder(AlwaysSensorBuilder.class)
                .setName("alwaysSensorIdle")
                .getBrick();

        ConditionalController controllerIdle = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .setName("controllerIdle")
                .getBrick();

        EditRigidBodyActuator editRigidBodyActuatorIdle = BricksUtils.getBuilder(EditRigidBodyActuatorBuilder.class)
                .setFriction(100)
                .setTargetRigidBody(bodyPlayer)
                .setName("editRigidBodyActuatorIdle")
                .getBrick();

        EffectActuator pauseIdleEffectActuator = BricksUtils.getBuilder(EffectActuatorBuilder.class)
                .setEffectView(particleEffectView)
                .setActive(false)
                .getBrick();

        MotionActuator motionActuatorIdle = BricksUtils.getBuilder(MotionActuatorBuilder.class)
                .setVelocity(new Vector2(0, 0))
                .setLimitVelocityX(7)
                .setName("motionActuatorWalkingRight")
                .getBrick();

        entityBuilder.addController(controllerIdle, "Idle")
                .connectToSensor(alwaysSensorIdle)
                .connectToActuators(editRigidBodyActuatorIdle, pauseIdleEffectActuator, motionActuatorIdle);


         /* State Walking ----------------------------------------------------------------
           ---------------------------------------------------------------------------------- */

        // ** Walking **
        AlwaysSensor alwaysSensorWalking = BricksUtils.getBuilder(AlwaysSensorBuilder.class)
                .setName("alwaysSensorWalking")
                .getBrick();

        ConditionalController controllerWalking = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .setName("controllerWalking")
                .getBrick();

        EditRigidBodyActuator editRigidBodyActuatorWalking = BricksUtils.getBuilder(EditRigidBodyActuatorBuilder.class)
                .setFriction(0.3f)
                .setTargetRigidBody(bodyPlayer)
                .setName("editRigidBodyActuatorWalking")
                .getBrick();

        EffectActuator activeEffectActuator = BricksUtils.getBuilder(EffectActuatorBuilder.class)
                .setEffectView(particleEffectView)
                .setActive(true)
                .getBrick();

        entityBuilder.addController(controllerWalking, "Walking")
                .connectToSensor(alwaysSensorWalking)
                .connectToActuators(editRigidBodyActuatorWalking, activeEffectActuator);

        //  ** Walking Right **
        KeyboardSensor keyboardSensorImpulseRight = BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                .setKeyCode(Input.Keys.D)
                .setPulse(Sensor.Pulse.PM_TRUE)
                .setName("keyboardSensorImpulseRight")
                .getBrick();

        ConditionalController controllerWalkingRight = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .setName("controllerWalkingRight")
                .getBrick();

        MotionActuator motionActuatorWalkingRight = BricksUtils.getBuilder(MotionActuatorBuilder.class)
                .setImpulse(new Vector2(2, 0))
                .setLimitVelocityX(7)
                .setName("motionActuatorWalkingRight")
                .getBrick();

        TextureActuator textureActuatorWalkingRight = BricksUtils.getBuilder(TextureActuatorBuilder.class)
                .setFlipX(false)
                .setTextureView(playerView)
                .setName("textureActuatorWalkingRight")
                .getBrick();

        entityBuilder.addController(controllerWalkingRight, "Walking")
                .connectToSensor(keyboardSensorImpulseRight)
                .connectToActuators(motionActuatorWalkingRight, textureActuatorWalkingRight);

        //  ** Walking Left **
        KeyboardSensor keyboardSensorImpulseLeft = BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                .setKeyCode(Input.Keys.A)
                .setPulse(Sensor.Pulse.PM_TRUE)
                .setName("keyboardSensorImpulseLeft")
                .getBrick();

        ConditionalController controllerWalkingLeft = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .setName("controllerWalkingLeft")
                .getBrick();

        MotionActuator motionActuatorWalkingLeft = BricksUtils.getBuilder(MotionActuatorBuilder.class)
                .setImpulse(new Vector2(-2, 0))
                .setLimitVelocityX(7)
                .setName("motionActuatorWalkingLeft")
                .getBrick();

        TextureActuator textureActuatorWalkingLeft = BricksUtils.getBuilder(TextureActuatorBuilder.class)
                .setFlipX(true)
                .setTextureView(playerView)
                .setName("textureActuatorWalkingLeft")
                .getBrick();

        entityBuilder.addController(controllerWalkingLeft, "Walking")
                .connectToSensor(keyboardSensorImpulseLeft)
                .connectToActuators(motionActuatorWalkingLeft, textureActuatorWalkingLeft);


          /* State Jump ----------------------------------------------------------------
           ---------------------------------------------------------------------------------- */

        AlwaysSensor alwaysSensorImpulseJump = BricksUtils.getBuilder(AlwaysSensorBuilder.class)
                .setName("alwaysSensorImpulseJump")
                .getBrick();

        ConditionalController controllerImpulseJump = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .setName("controllerImpulseJump")
                .getBrick();

        MotionActuator motionActuatorJump = BricksUtils.getBuilder(MotionActuatorBuilder.class)
                .setImpulse(new Vector2(0, 8))
                .setName("motionActuatorJump")
                .getBrick();

        EffectActuator pauseJumpEffectActuator = BricksUtils.getBuilder(EffectActuatorBuilder.class)
                .setEffectView(particleEffectView)
                .setActive(false)
                .getBrick();

        entityBuilder.addController(controllerImpulseJump, "Jump")
                .connectToSensor(alwaysSensorImpulseJump)
                .connectToActuators(motionActuatorJump, pauseJumpEffectActuator);

        return entityBuilder.getEntity();

    }

}

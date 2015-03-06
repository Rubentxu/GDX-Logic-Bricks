package com.indignado.functional.test.levels.simpleplatform.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.indignado.logicbricks.components.CameraComponent;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.actuators.CameraActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.AlwaysSensor;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.LBBuilders;
import com.indignado.logicbricks.utils.builders.actuators.CameraActuatorBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.AlwaysSensorBuilder;

/**
 * @author Rubentxu.
 */
public class PlayerCamera extends EntityFactory {


    public PlayerCamera(LBBuilders builders, AssetManager assetManager) {
        super(builders, assetManager);

    }


    @Override
    public void loadAssets() {
    }


    @Override
    public Entity createEntity() {
        EntityBuilder entityBuilder = builders.getEntityBuilder();
        entityBuilder.initialize();
        BodyBuilder bodyBuilder = builders.getBodyBuilder();

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Camera";
        //identity.category = game.getCategoryBitsManager().getCategoryBits("Crate");

        StateComponent state = entityBuilder.getComponent(StateComponent.class);
        state.createState("Default");

        CameraComponent camera = entityBuilder.getComponent(CameraComponent.class);


        // Create Bricks Camera ----------------------------------------------------------------
        AlwaysSensor alwaysSensorCamera = builders.getBrickBuilder(AlwaysSensorBuilder.class)
                .setPulse(Sensor.Pulse.PM_TRUE)
                .setName("alwaysSensorCamera")
                .getBrick();

        ConditionalController controllerCamera = builders.getBrickBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .setName("controllerCamera")
                .getBrick();

        CameraActuator cameraActuator = builders.getBrickBuilder(CameraActuatorBuilder.class)
                .setHeight((short) 1)
                .setCamera(camera.camera)
                .setFollowTagEntity("Player")
                .setName("cameraActuator")
                .getBrick();

        entityBuilder.addController(controllerCamera, "Default")
                .connectToSensor(alwaysSensorCamera)
                .connectToActuator(cameraActuator);

        Entity entity = entityBuilder.getEntity();
        Gdx.app.log("Crate", "instance" + entity);
        return entity;

    }


}

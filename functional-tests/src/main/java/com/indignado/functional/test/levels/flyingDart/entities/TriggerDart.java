package com.indignado.functional.test.levels.flyingDart.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.functional.test.levels.flyingDart.MousePositionScript;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.core.actuators.InstanceEntityActuator;
import com.indignado.logicbricks.core.controllers.ScriptController;
import com.indignado.logicbricks.core.sensors.AlwaysSensor;
import com.indignado.logicbricks.core.sensors.MouseSensor;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;
import com.indignado.logicbricks.utils.builders.actuators.InstanceEntityActuatorBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ScriptControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.AlwaysSensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.MouseSensorBuilder;

/**
 * @author Rubentxu.
 */
public class TriggerDart extends EntityFactory {
    private String dartTexture = "assets/textures/dart.png";


    public TriggerDart(Game game) {
        super(game);
    }


    @Override
    public void loadAssets() {
        if (!game.getAssetManager().isLoaded(dartTexture)) game.getAssetManager().load(dartTexture, Texture.class);

    }


    @Override
    public Entity createEntity() {
        EntityBuilder entityBuilder = game.getEntityBuilder();
        entityBuilder.initialize();
        BodyBuilder bodyBuilder = game.getBodyBuilder();

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Trigger";
        identity.category = game.getCategoryBitsManager().getCategoryBits("TriggerDart");
        identity.collisionMask = (short) ~identity.category;


        Body bodyTrigger = bodyBuilder.fixture(new FixtureDefBuilder()
                .boxShape(1, 2))
                .type(BodyDef.BodyType.StaticBody)
                .build();

        entityBuilder.addRigidBody(bodyTrigger);


        MouseSensor trigger = game.getBuilder(MouseSensorBuilder.class)
                .setMouseEvent(MouseSensor.MouseEvent.LEFT_BUTTON_DOWN)
                .setFrequency(0.7f)
                .setPulse(Sensor.Pulse.PM_TRUE)
                .setName("SensorMouse")
                .getBrick();

        AlwaysSensor alwaysSensor = game.getBuilder(AlwaysSensorBuilder.class)
                .setPulse(Sensor.Pulse.PM_TRUE)
                .setName("DelayTrigger")
                .getBrick();


        ScriptController controllerTrigger = game.getBuilder(ScriptControllerBuilder.class)
                .setScript(new MousePositionScript())
                .setName("MousePosition")
                .getBrick();


        InstanceEntityActuator instanceEntityActuator = game.getBuilder(InstanceEntityActuatorBuilder.class)
                .setType(InstanceEntityActuator.Type.AddEntity)
                .setEntityFactory(game.getEntityFactories().get(Dart.class))
                .setLocalPosition(new Vector2(2, 1))
                .setDuration(5f)
                .setName("ActuatorInstanceDart")
                .getBrick();


        Entity entity = entityBuilder
                .addController(controllerTrigger, "Default")
                .connectToSensors(trigger, alwaysSensor)
                .connectToActuator(instanceEntityActuator)
                .getEntity();

        Gdx.app.log("TriggerDart", "instance" + entity);
        return entity;
    }


}

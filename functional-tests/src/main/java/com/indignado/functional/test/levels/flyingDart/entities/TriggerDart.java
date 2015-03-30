package com.indignado.functional.test.levels.flyingDart.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.functional.test.levels.flyingDart.MousePositionScript;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.actuators.InstanceEntityActuator;
import com.indignado.logicbricks.core.controllers.ScriptController;
import com.indignado.logicbricks.core.data.RigidBody2D;
import com.indignado.logicbricks.core.data.TextureView;
import com.indignado.logicbricks.core.sensors.AlwaysSensor;
import com.indignado.logicbricks.core.sensors.MouseSensor;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;
import com.indignado.logicbricks.utils.builders.LBBuilders;
import com.indignado.logicbricks.utils.builders.actuators.InstanceEntityActuatorBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ScriptControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.AlwaysSensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.MouseSensorBuilder;

/**
 * @author Rubentxu.
 */
public class TriggerDart extends EntityFactory {
    private String hand = "assets/textures/hand.png";


    public TriggerDart(LBBuilders builders, AssetManager assetManager) {
        super(builders, assetManager);

    }


    @Override
    public void loadAssets() {
        if (!assetManager.isLoaded(hand)) assetManager.load(hand, Texture.class);

    }


    @Override
    public Entity createEntity(float x, float y, float z) {
        EntityBuilder entityBuilder = builders.getEntityBuilder();
        entityBuilder.initialize();
        BodyBuilder bodyBuilder = builders.getBodyBuilder();

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Trigger";
   /*     identity.category = game.getCategoryBitsManager().getCategoryBits("TriggerDart");
        identity.collisionMask = (short) ~identity.category;*/


        RigidBody2D bodyTrigger = bodyBuilder.fixture(new FixtureDefBuilder()
                .boxShape(1, 2))
                .type(BodyDef.BodyType.StaticBody)
                .position(x, y)
                .build();

        entityBuilder.addRigidBody(bodyTrigger);


        MouseSensor trigger = builders.getBrickBuilder(MouseSensorBuilder.class)
                .setMouseEvent(MouseSensor.MouseEvent.LEFT_BUTTON_DOWN)
                .setFrequency(0.7f)
                .setPulse(Sensor.Pulse.PM_TRUE)
                .setName("SensorMouse")
                .getBrick();

        AlwaysSensor alwaysSensor =  builders.getBrickBuilder(AlwaysSensorBuilder.class)
                .setPulse(Sensor.Pulse.PM_TRUE)
                .setName("DelayTrigger")
                .getBrick();


        ScriptController controllerTrigger =  builders.getBrickBuilder(ScriptControllerBuilder.class)
                .setScript(new MousePositionScript())
                .setName("MousePosition")
                .getBrick();


        InstanceEntityActuator instanceEntityActuator =  builders.getBrickBuilder(InstanceEntityActuatorBuilder.class)
                .setType(InstanceEntityActuator.Type.AddEntity)
                .setEntityFactory(Dart.class)
                .setLocalPosition(new Vector2(2, 1))
                .setDuration(5f)
                .setName("ActuatorInstanceDart")
                .getBrick();


        TextureView triggerView = new TextureView();
        triggerView.setName("trigger");
        triggerView.setTextureRegion(new TextureRegion(assetManager.get(hand, Texture.class)));
        triggerView.transform.scaleX = 3;
        triggerView.transform.scaleY = 5f;
        triggerView.transform.rigidBody = bodyTrigger;
        triggerView.setLayer(0);

        ViewsComponent viewsComponent = entityBuilder.getComponent(ViewsComponent.class);
        viewsComponent.views.add(triggerView);

        Entity entity = entityBuilder
                .addController(controllerTrigger, "Default")
                .connectToSensors(trigger, alwaysSensor)
                .connectToActuator(instanceEntityActuator)
                .getEntity();

        Gdx.app.log("TriggerDart", "instance" + entity);
        return entity;

    }


}

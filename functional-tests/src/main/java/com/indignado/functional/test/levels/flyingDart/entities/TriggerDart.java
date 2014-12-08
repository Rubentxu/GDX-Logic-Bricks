package com.indignado.functional.test.levels.flyingDart.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.functional.test.levels.flyingDart.CalculateVelocityScript;
import com.indignado.functional.test.levels.flyingDart.MousePositionScript;
import com.indignado.logicbricks.components.*;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.core.actuators.InstanceEntityActuator;
import com.indignado.logicbricks.core.actuators.MessageActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.controllers.ScriptController;
import com.indignado.logicbricks.core.sensors.MouseSensor;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;
import com.indignado.logicbricks.utils.builders.actuators.InstanceEntityActuatorBuilder;
import com.indignado.logicbricks.utils.builders.actuators.MessageActuatorBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ScriptControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.MouseSensorBuilder;

/**
 * @author Rubentxu.
 */
public class TriggerDart extends EntityFactory {
    private String dartTexture = "assets/textures/dart.png";


    public TriggerDart(World world) {
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

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Trigger";
        identity.category = world.getCategoryBitsManager().getCategoryBits("TriggerDart");
        identity.collisionMask = (short) ~identity.category;


        Body bodyArrow = bodyBuilder.fixture(new FixtureDefBuilder()
                .boxShape(1, 2))
                .type(BodyDef.BodyType.StaticBody)
                .build();

        entityBuilder.addRigidBody(bodyArrow);

      /*  TextureView arrowView = new TextureView();
        arrowView.setName("Arrow");
        arrowView.setTextureRegion(new TextureRegion(world.getAssetManager().get(dartTexture, Texture.class)));
        arrowView.setHeight(1f);
        arrowView.setWidth(2.3f);
        arrowView.setAttachedTransform(bodyArrow.getTransform());
        arrowView.setLayer(0);

        entityBuilder.addView(arrowView);*/

        MouseSensor trigger = BricksUtils.getBuilder(MouseSensorBuilder.class)
                .setMouseEvent(MouseSensor.MouseEvent.LEFT_BUTTON)
                .setFrequency(1)
                .setName("SensorMouse")
                .getBrick();


        ScriptController mousePositionScript = BricksUtils.getBuilder(ScriptControllerBuilder.class)
                .setScript(new MousePositionScript())
                .setName("MousePosition")
                .getBrick();


        InstanceEntityActuator instanceEntityActuator = BricksUtils.getBuilder(InstanceEntityActuatorBuilder.class)
                .setType(InstanceEntityActuator.Type.AddEntity)
                .setEntityFactory(world.getEntityFactories().get(Dart.class))
                .setLocalPosition(new Vector2(2, 1))
                .setDuration(4.5f)
                .setName("ActuatorInstanceDart")
                .getBrick();


        Entity entity = entityBuilder
                .addController(mousePositionScript, "Default")
                .connectToSensor(trigger)
                .connectToActuator(instanceEntityActuator)
                .getEntity();

        Gdx.app.log("TriggerDart","instance" + entity);
        return entity;
    }


}

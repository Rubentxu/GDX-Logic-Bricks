package com.indignado.functional.test.levels.radialgravity.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.core.actuators.MotionActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.data.TextureView;
import com.indignado.logicbricks.core.sensors.AlwaysSensor;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;
import com.indignado.logicbricks.utils.builders.actuators.MotionActuatorBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.AlwaysSensorBuilder;

/**
 * @author Rubentxu.
 */
public class Cohete extends EntityFactory {
    private String cohete = "assets/textures/Cohete.png";

    public Cohete(Game game) {
        super(game);

    }


    @Override
    public void loadAssets() {
        if (!game.getAssetManager().isLoaded(cohete)) game.getAssetManager().load(cohete, Texture.class);
    }


    @Override
    public Entity createEntity() {
        EntityBuilder entityBuilder = game.getEntityBuilder();
        entityBuilder.initialize();
        BodyBuilder bodyBuilder = game.getBodyBuilder();

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Box";

        StateComponent state = entityBuilder.getComponent(StateComponent.class);
        state.createState("Default");

        Body bodyCohete = bodyBuilder.fixture(new FixtureDefBuilder()
                .boxShape(MathUtils.random(0.4f, 0.8f), MathUtils.random(0.4f, 0.8f))
                .friction(1f)
                .density(2f))
                .mass(2)
                .type(BodyDef.BodyType.DynamicBody)
                .build();

        AlwaysSensor alwaysSensor = game.getBuilder(AlwaysSensorBuilder.class)
                .setPulse(Sensor.Pulse.PM_TRUE)
                .setName("alwaysSensor")
                .getBrick();

        ConditionalController controller = game.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();

        MotionActuator motionActuator = game.getBuilder(MotionActuatorBuilder.class)
                .setTorque(5)
                .getBrick();


        RigidBodiesComponents bodiesComponents = entityBuilder.getComponent(RigidBodiesComponents.class);
        bodiesComponents.rigidBodies.add(bodyCohete);


        TextureView coheteView = new TextureView();
        coheteView.setName("Cohete");
        coheteView.setTextureRegion(new TextureRegion(game.getAssetManager().get(cohete, Texture.class)));
        coheteView.setHeight(2.5f);
        coheteView.setWidth(2.5f);
        coheteView.setAttachedTransform(bodyCohete.getTransform());
        coheteView.setLayer(0);

        ViewsComponent viewsComponent = entityBuilder.getComponent(ViewsComponent.class);
        viewsComponent.views.add(coheteView);


        Entity entity = entityBuilder.addController(controller, "Default")
                .connectToSensor(alwaysSensor)
                .connectToActuator(motionActuator)
                .getEntity();

        Gdx.app.log("Box", "instance" + entity);
        return entity;

    }


}

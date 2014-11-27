package com.indignado.functional.test.entities;

import com.badlogic.gdx.Input;
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
import com.indignado.logicbricks.components.data.RigidBody;
import com.indignado.logicbricks.components.data.TextureView;
import com.badlogic.ashley.core.LogicEntity;
import com.indignado.logicbricks.core.actuators.MotionActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.KeyboardSensor;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;
import com.indignado.logicbricks.utils.builders.actuators.MotionActuatorBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.KeyboardSensorBuilder;

/**
 * @author Rubentxu.
 */
public class Dart extends LogicEntity {


    @Override
    public void create(com.indignado.logicbricks.core.World world) {
        BodyBuilder bodyBuilder = world.getBodyBuilder();

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

        Body bodyArrow = bodyBuilder.fixture(new FixtureDefBuilder()
                .polygonShape(vertices)
                .friction(0.5f)
                .restitution(0.5f))
                .type(BodyDef.BodyType.DynamicBody)
                .position(0, 0)
                .bullet()
                .userData(context)
                .build();

        RigidBodiesComponents bodiesComponents = new RigidBodiesComponents();
        RigidBody rigidBody = new RigidBody();
        rigidBody.body = bodyArrow;
        bodiesComponents.rigidBodies.add(rigidBody);
        this.add(bodiesComponents);

        TextureView arrowView = new TextureView();
        arrowView.setName("Arrow");
        world.getAssetManager().load("assets/textures/dart.png", Texture.class);
        world.getAssetManager().finishLoading();
        Texture tex = null;
        if( world.getAssetManager().isLoaded("assets/textures/dart.png")) {
            tex =  world.getAssetManager().get("assets/textures/dart.png", Texture.class);
        }
        arrowView.setTextureRegion(new TextureRegion(tex));
        arrowView.setHeight(0.4f);
        arrowView.setWidth(2.5f);
        arrowView.setAttachedTransform(bodyArrow.getTransform());
        arrowView.setLayer(0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(arrowView);

        this.add(viewsComponent);

        KeyboardSensor initArrow = BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                .setKeyCode(Input.Keys.A).getBrick();

        ConditionalController arrowController = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setType(ConditionalController.Type.AND).getBrick();

        MotionActuator motionActuator = BricksUtils.getBuilder(MotionActuatorBuilder.class)
                .setImpulse(new Vector2((float) (20 * Math.cos(0)), (float) (20 * Math.sin(0))))
                .setOwner(this).getBrick();

        world.getEntityBuilder().addController(arrowController, "Default")
                .connectToSensor(initArrow)
                .connectToActuator(motionActuator)
                .build(this);

    }


    @Override
    public void init(float posX, float posY, float angle) {
        RigidBodiesComponents rbc = this.getComponent(RigidBodiesComponents.class);
        for(RigidBody rigidBody : rbc.rigidBodies) {
            Vector2 centroidPosition = new Vector2(posX, posY);
            centroidPosition.add(rigidBody.localPosition);
            rigidBody.body.setTransform(centroidPosition,angle);

        }
    }
}

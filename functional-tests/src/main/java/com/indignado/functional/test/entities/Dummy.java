package com.indignado.functional.test.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.EntityBuilder;
import com.indignado.logicbricks.core.actuators.InstanceEntityActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.KeyboardSensor;
import com.indignado.logicbricks.utils.box2d.BodyBuilder;
import com.indignado.logicbricks.utils.box2d.FixtureDefBuilder;

/**
 * @author Rubentxu.
 */
public class Dummy extends Entity {

    public Dummy(Engine engine, World world, Vector2 position) {

        StateComponent state = new StateComponent();

        Body bodyDummy = new BodyBuilder(world).fixture(new FixtureDefBuilder()
                .boxShape(0.6f, 1))
                .type(BodyDef.BodyType.StaticBody)
                .position(position.x, position.y)
                .build();

        RigidBodiesComponents bodiesComponents = new RigidBodiesComponents();
        bodiesComponents.rigidBodies.add(bodyDummy);
        this.add(bodiesComponents);


        EntityBuilder logicBuilder = new EntityBuilder(engine);

        logicBuilder.addController(logicBuilder.controller(ConditionalController.class)
                .setType(ConditionalController.Type.AND), "Default")
                .connectToSensor(logicBuilder.sensor(KeyboardSensor.class)
                        .setKeyCode(Input.Keys.A))
                .connectToActuator(logicBuilder.actuator(InstanceEntityActuator.class)
                        .setDuration(10)
                        .setType(Dart.class));


    }
}

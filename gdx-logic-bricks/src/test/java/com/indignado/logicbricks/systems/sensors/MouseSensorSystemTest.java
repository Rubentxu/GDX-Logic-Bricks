package com.indignado.logicbricks.systems.sensors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.data.LogicBrick;
import com.indignado.logicbricks.core.sensors.MouseSensor;
import com.indignado.logicbricks.systems.sensors.base.ActuatorTest;
import com.indignado.logicbricks.systems.sensors.base.BaseSensorSystemTest;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.MouseSensorBuilder;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author Rubentxu
 */
public class MouseSensorSystemTest extends BaseSensorSystemTest<MouseSensor, MouseSensorSystem> {

    private Transform transform;


    @Override
    protected void tearDown() {

    }


    @Override
    protected void createContext() {
        // Create Player Entity
        entityBuilder.initialize();
        IdentityComponent identityPlayer = entityBuilder.getComponent(IdentityComponent.class);
        identityPlayer.tag = "Player";
        transform = new Transform(new Vector2(), 0);
       /* TextureView view = new TextureView();
        view.attachedTransform = transform;
        view.width = 4;
        view.height = 3;
        entityBuilder.getComponent(ViewsComponent.class).views.add(view);
*/
        sensor =builders.getBrickBuilder(MouseSensorBuilder.class)
                .setMouseEvent(MouseSensor.MouseEvent.MOVEMENT)
                .setName("sensorPlayer")
                .getBrick();

        ConditionalController controllerGround =builders.getBrickBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();

        ActuatorTest actuatorTest = new ActuatorTest();

        player = entityBuilder
                .addController(controllerGround, "Default")
                .connectToSensor(sensor)
                .connectToActuator(actuatorTest)
                .getEntity();
        sensorSystem = engine.getSystem(MouseSensorSystem.class);

    }


    @Test
    public void mouseSensorMovementEventTest() {
        engine.addEntity(player);

        sensorSystem.mouseMoved(50, 50);
        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

    }


    //@Test
    public void mouseSensorMouseOvertEventTest() {
        sensor.mouseEvent = (MouseSensor.MouseEvent.MOUSE_OVER);
        sensor.target = (player);
        engine.addEntity(player);

        transform.setPosition(new Vector2(24, 24));
        sensorSystem.mouseMoved(25, 25);
        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertTrue(sensor.positive);

        sensorSystem.mouseMoved(28, 28);
        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);

    }


    @Test
    public void mouseSensorWheelDownEventTest() {
        sensor.mouseEvent = MouseSensor.MouseEvent.WHEEL_DOWN;
        engine.addEntity(player);

        sensorSystem.scrolled(-1);
        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);

    }


    @Test
    public void mouseSensorWheelUpEventTest() {
        sensor.mouseEvent = MouseSensor.MouseEvent.WHEEL_UP;
        engine.addEntity(player);

        sensorSystem.scrolled(1);
        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);

    }


    @Test
    public void leftButtonDownEventTest() {
        sensor.mouseEvent = (MouseSensor.MouseEvent.LEFT_BUTTON_DOWN);
        engine.addEntity(player);


        sensorSystem.touchDown(5, 5, 1, Input.Buttons.LEFT);
        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertTrue(sensor.positive);

        sensorSystem.touchUp(5, 5, 1, Input.Buttons.LEFT);
        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);

    }


    @Test
    public void middleButtonDownEventTest() {
        sensor.mouseEvent = (MouseSensor.MouseEvent.MIDDLE_BUTTON_DOWN);
        engine.addEntity(player);


        sensorSystem.touchDown(5, 5, 1, Input.Buttons.MIDDLE);
        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertTrue(sensor.positive);

        sensorSystem.touchUp(5, 5, 1, Input.Buttons.MIDDLE);
        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);

    }


    @Test
    public void rightButtonDownEventTest() {
        sensor.mouseEvent = (MouseSensor.MouseEvent.RIGHT_BUTTON_DOWN);
        engine.addEntity(player);


        sensorSystem.touchDown(5, 5, 1, Input.Buttons.RIGHT);
        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertTrue(sensor.positive);

        sensorSystem.touchUp(5, 5, 1, Input.Buttons.RIGHT);
        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);

    }


    @Test
    public void leftButtonUpEventTest() {
        sensor.mouseEvent = (MouseSensor.MouseEvent.LEFT_BUTTON_UP);
        engine.addEntity(player);

        sensorSystem.touchUp(5, 5, 1, Input.Buttons.LEFT);
        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);

    }


    @Test
    public void middleButtonUpEventTest() {
        sensor.mouseEvent = (MouseSensor.MouseEvent.MIDDLE_BUTTON_UP);
        engine.addEntity(player);

        sensorSystem.touchUp(5, 5, 1, Input.Buttons.MIDDLE);
        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);

    }


    @Test
    public void rightButtonUpEventTest() {
        sensor.mouseEvent = (MouseSensor.MouseEvent.RIGHT_BUTTON_UP);
        engine.addEntity(player);

        sensorSystem.touchUp(5, 5, 1, Input.Buttons.RIGHT);
        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        game.singleStep(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);

    }


}

package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.components.data.TextureView;
import com.indignado.logicbricks.components.data.View;
import com.indignado.logicbricks.components.sensors.MouseSensorComponent;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.core.sensors.CollisionSensor;
import com.indignado.logicbricks.core.sensors.MouseSensor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Rubentxu
 */
public class MouseSensorSystem extends SensorSystem<MouseSensor, MouseSensorComponent> implements InputProcessor, EntityListener {
    private Set<MouseSensor> mouseSensors;
    private World world;

    public MouseSensorSystem(World world) {
        super(MouseSensorComponent.class);
        mouseSensors = new HashSet<MouseSensor>();
        this.world = world;

    }


    @Override
    public boolean processSensor(MouseSensor sensor, float deltaTime) {
        boolean isActive = false;

        if (sensor.mouseEventSignal) {
            switch (sensor.mouseEvent) {
                case MOUSE_OVER:
                    sensor.mouseEventSignal = false;
                    if (sensor.target == null) isActive = false;
                    isActive = isMouseOver(sensor.target, sensor.positionXsignal, sensor.positionYsignal);
                    break;
                case MOVEMENT:
                case WHEEL_DOWN:
                case WHEEL_UP:
                default:
                    sensor.mouseEventSignal = false;
                    isActive = true;
            }
        }
        sensor.pulseSignal = isActive;
        return sensor.pulseSignal;

    }


    public boolean isMouseOver(Entity target, int posX, int posY) {
        ViewsComponent viewsComponent = target.getComponent(ViewsComponent.class);
        if (viewsComponent == null) return false;

        Rectangle rectangle = new Rectangle();
        for (Object view : viewsComponent.views) {
            if (TextureView.class.isAssignableFrom(view.getClass())) {
                TextureView textureView = (TextureView) view;
                rectangle.set(textureView.attachedTransform.getPosition().x - textureView.width / 2,
                        textureView.attachedTransform.getPosition().y - textureView.height / 2, textureView.width, textureView.height);
                if (rectangle.contains(posX, posY)) return true;
            }
        }
        return false;

    }


    @Override
    public boolean keyDown(int keycode) {
        return false;

    }


    @Override
    public boolean keyUp(int keycode) {
        return false;

    }


    @Override
    public boolean keyTyped(char character) {
        return false;

    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (MouseSensor sensor : mouseSensors) {
            switch (button) {
                case Input.Buttons.LEFT:
                    if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.LEFT_BUTTON)) {
                        sensor.mouseEventSignal = true;
                        sensor.buttonUP = false;
                    }
                    break;
                case Input.Buttons.MIDDLE:
                    if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.MIDDLE_BUTTON)) {
                        sensor.mouseEventSignal = true;
                        sensor.buttonUP = false;
                    }
                    break;
                case Input.Buttons.RIGHT:
                    if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.RIGHT_BUTTON)) {
                        sensor.mouseEventSignal = true;
                        sensor.buttonUP = false;
                    }
                    break;
            }
            Vector3 worldCoordinates = new Vector3(screenX, screenY, 0);
            world.getCamera().unproject(worldCoordinates);

            sensor.positionXsignal = (int) worldCoordinates.x;
            sensor.positionYsignal = (int) worldCoordinates.y;

        }
        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (MouseSensor sensor : mouseSensors) {
            switch (button) {
                case Input.Buttons.LEFT:
                    if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.LEFT_BUTTON)) {
                        sensor.mouseEventSignal = false;
                        sensor.buttonUP = true;
                    }
                    break;
                case Input.Buttons.MIDDLE:
                    if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.MIDDLE_BUTTON)) {
                        sensor.mouseEventSignal = false;
                        sensor.buttonUP = true;
                    }
                    break;
                case Input.Buttons.RIGHT:
                    if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.RIGHT_BUTTON)) {
                        sensor.mouseEventSignal = false;
                        sensor.buttonUP = true;
                    }
                    break;
            }

        }
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        for (MouseSensor sensor : mouseSensors) {
            if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.MOVEMENT) ||
                    sensor.mouseEvent.equals(MouseSensor.MouseEvent.MOUSE_OVER)) {
                sensor.positionXsignal = screenX;
                sensor.positionYsignal = screenY;
                sensor.mouseEventSignal = true;
            }
        }
        return false;

    }


    @Override
    public boolean scrolled(int amount) {
        for (MouseSensor sensor : mouseSensors) {
            sensor.mouseEventSignal = false;
            if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.WHEEL_DOWN) && amount < 0) {
                sensor.mouseEventSignal = true;
            }
            if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.WHEEL_UP) && amount > 0) {
                sensor.mouseEventSignal = true;
            }
            sensor.amountScrollSignal = amount;
        }
        return false;

    }


    @Override
    public void entityAdded(Entity entity) {
        MouseSensorComponent mouseComponent = entity.getComponent(MouseSensorComponent.class);
        if (mouseComponent != null) {
            IntMap<ObjectSet<MouseSensor>> map = mouseComponent.sensors;
            for (int i = 0; i < map.size; ++i) {
                ObjectSet.ObjectSetIterator<MouseSensor> it = map.get(i).iterator();
                while (it.hasNext()){
                    mouseSensors.add(it.next());
                }
            }
        }

    }

    @Override
    public void entityRemoved(Entity entity) {
        MouseSensorComponent mouseComponent = entity.getComponent(MouseSensorComponent.class);
        if (mouseComponent != null) {
            IntMap<ObjectSet<MouseSensor>> map = mouseComponent.sensors;
            for (int i = 0; i < map.size; ++i) {
                ObjectSet.ObjectSetIterator<MouseSensor> it = map.get(i).iterator();
                while (it.hasNext()){
                    mouseSensors.remove(it.next());
                }
            }
        }
    }
}

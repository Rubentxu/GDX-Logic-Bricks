package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.components.sensors.MouseSensorComponent;
import com.indignado.logicbricks.core.data.TextureView;
import com.indignado.logicbricks.core.sensors.MouseSensor;
import com.indignado.logicbricks.core.sensors.MouseSensor.MouseEvent;

/**
 * @author Rubentxu
 */
public class MouseSensorSystem extends SensorSystem<MouseSensor, MouseSensorComponent> implements InputProcessor, EntityListener {
    private ObjectMap<MouseEvent, ObjectSet<MouseSensor>> mouseSensors;
    private Camera camera;


    public MouseSensorSystem() {
        super(MouseSensorComponent.class);
        mouseSensors = new ObjectMap<MouseEvent, ObjectSet<MouseSensor>>();

    }


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.camera = context.get(Camera.class);

    }


    @Override
    public boolean query(MouseSensor sensor, float deltaTime) {
        boolean isActive = false;

        if (sensor.mouseEventSignal != null && sensor.mouseEvent.equals(sensor.mouseEventSignal)) {
            isActive = true;

        }

        if (sensor.mouseEvent.equals(MouseEvent.MOUSE_OVER)) {
            isActive = isMouseOver(sensor.target, (int) sensor.positionSignal.x, (int) sensor.positionSignal.y);

        }

        if (!sensor.mouseEvent.equals(MouseEvent.RIGHT_BUTTON_DOWN) && !sensor.mouseEvent.equals(MouseEvent.MIDDLE_BUTTON_DOWN)
                && !sensor.mouseEvent.equals(MouseEvent.LEFT_BUTTON_DOWN)) {
            sensor.mouseEventSignal = null;

        }
        return isActive;

    }


    public boolean isMouseOver(Entity target, int posX, int posY) {
        if (target == null) return false;
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
        Vector3 worldCoordinates = new Vector3(screenX, screenY, 0);
        camera.unproject(worldCoordinates);

        switch (button) {
            case Input.Buttons.LEFT:
                changeSensors(MouseEvent.LEFT_BUTTON_DOWN, worldCoordinates);
                break;
            case Input.Buttons.MIDDLE:
                changeSensors(MouseEvent.MIDDLE_BUTTON_DOWN, worldCoordinates);
                break;
            case Input.Buttons.RIGHT:
                changeSensors(MouseEvent.RIGHT_BUTTON_DOWN, worldCoordinates);
                break;

        }
        return false;

    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 worldCoordinates = new Vector3(screenX, screenY, 0);
        camera.unproject(worldCoordinates);

        switch (button) {
            case Input.Buttons.LEFT:
                changeSensors(MouseEvent.LEFT_BUTTON_UP, worldCoordinates);
                changeSensors(MouseEvent.LEFT_BUTTON_DOWN, worldCoordinates, false);
                break;
            case Input.Buttons.MIDDLE:
                changeSensors(MouseEvent.MIDDLE_BUTTON_UP, worldCoordinates);
                changeSensors(MouseEvent.MIDDLE_BUTTON_DOWN, worldCoordinates, false);
                break;
            case Input.Buttons.RIGHT:
                changeSensors(MouseEvent.RIGHT_BUTTON_UP, worldCoordinates);
                changeSensors(MouseEvent.RIGHT_BUTTON_DOWN, worldCoordinates, false);
                break;

        }
        return false;

    }


    private void changeSensors(MouseEvent event, Vector3 coordinates) {
        changeSensors(event, coordinates, true);

    }


    private void changeSensors(MouseEvent event, Vector3 coordinates, boolean active) {
        if (mouseSensors.containsKey(event)) {
            for (MouseSensor sensor : mouseSensors.get(event)) {
                if (active) sensor.mouseEventSignal = event;
                else sensor.mouseEventSignal = null;
                sensor.positionSignal.set(coordinates.x, coordinates.y);

            }
        }

    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector3 worldCoordinates = new Vector3(screenX, screenY, 0);
        camera.unproject(worldCoordinates);
        changeSensors(MouseEvent.MOVEMENT, worldCoordinates);
        changeSensors(MouseEvent.MOUSE_OVER, worldCoordinates);
        return false;

    }


    @Override
    public boolean scrolled(int amount) {
        if (amount < 0) {
            if (mouseSensors.containsKey(MouseEvent.WHEEL_DOWN)) {
                for (MouseSensor sensor : mouseSensors.get(MouseEvent.WHEEL_DOWN)) {
                    sensor.mouseEventSignal = MouseEvent.WHEEL_DOWN;
                    sensor.amountScrollSignal = amount;
                }
            }
        }
        if (amount > 0) {
            if (mouseSensors.containsKey(MouseEvent.WHEEL_UP)) {
                for (MouseSensor sensor : mouseSensors.get(MouseEvent.WHEEL_UP)) {
                    sensor.mouseEventSignal = MouseEvent.WHEEL_UP;
                    sensor.amountScrollSignal = amount;
                }
            }
        }
        return false;

    }


    @Override
    public void entityAdded(Entity entity) {
        MouseSensorComponent mouseComponent = entity.getComponent(MouseSensorComponent.class);
        if (mouseComponent != null) {
            IntMap.Values<ObjectSet<MouseSensor>> values = mouseComponent.sensors.values();
            while (values.hasNext()) {
                for (MouseSensor sensor : values.next()) {
                    ObjectSet eventSensors;
                    if (mouseSensors.containsKey(sensor.mouseEvent)) {
                        eventSensors = mouseSensors.get(sensor.mouseEvent);

                    } else {
                        eventSensors = new ObjectSet<MouseSensor>();
                        mouseSensors.put(sensor.mouseEvent, eventSensors);
                    }
                    eventSensors.add(sensor);

                }
            }
        }

    }


    @Override
    public void entityRemoved(Entity entity) {
        MouseSensorComponent mouseComponent = entity.getComponent(MouseSensorComponent.class);
        if (mouseComponent != null) {
            IntMap.Values<ObjectSet<MouseSensor>> values = mouseComponent.sensors.values();
            while (values.hasNext()) {
                for (MouseSensor sensor : values.next()) {
                    if (mouseSensors.containsKey(sensor.mouseEvent)) {
                        mouseSensors.get(sensor.mouseEvent).remove(sensor);

                    }
                }
            }
        }
    }

}

package com.indignado.games.bricks.sensors;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSensor<T> extends Sensor {

    enum Collision { FULL , PARTIAL }

    // Config Values
    public Collision collision;

    // Signal Values
    public Collider colliderSignal;
    public T targetSignal;


    public CollisionSensor(T owner) {
        super(owner);
    }


    @Override
    public Boolean isActive() {
        if (isTap()) return false;

        if (mouseEvent.equals(mouseEventSignal)) {
            if (mouseEvent.equals(MouseEvent.MOUSE_OVER)) {
                if (targetSignal.equals(owner)) return true;
                else return false;
            }
            return true;
        }
        return false;

    }

}

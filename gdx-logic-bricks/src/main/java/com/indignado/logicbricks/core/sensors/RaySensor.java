package com.indignado.logicbricks.core.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.core.LogicBricksException;
import com.indignado.logicbricks.core.data.Axis2D;


/**
 * @author Rubentxu
 */
public class RaySensor extends Sensor implements RayCastCallback {

    // Config Values
    public String targetTag;
    public String targetPropertyName;
    public Axis2D axis2D = Axis2D.Xpositive;
    public float range = 0;
    public boolean xRayMode = false;
    public Body attachedRigidBody;

    // Signal Values
    public ObjectSet<Entity> contacts = new ObjectSet<Entity>();

    @Override
    public void reset() {
        super.reset();
        targetTag = null;
        targetPropertyName = null;
        axis2D = Axis2D.Xpositive;;
        range = 0;
        xRayMode = false;
        contacts.clear();

    }


    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        Entity entity = (Entity) fixture.getBody().getUserData();
        if(entity == null) throw new LogicBricksException("RaySensor","The userdata fixture must return an entity");

        if(targetTag != null && targetTag.equals(entity.getComponent(IdentityComponent.class).tag)) {
            contacts.add(entity);

        } else if(targetPropertyName != null && entity.getComponent(BlackBoardComponent.class) != null
                && entity.getComponent(BlackBoardComponent.class).hasProperty(targetPropertyName)) {
            contacts.add(entity);

        } else if(targetTag != null && targetPropertyName != null) {
            contacts.add(entity);

        }

        if(xRayMode) return 1 ;
        else return 0;

    }

}
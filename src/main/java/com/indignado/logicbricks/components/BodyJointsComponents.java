package com.indignado.logicbricks.components;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointEdge;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 17/10/14.
 *
 * @author Rubentxu
 */
public class BodyJointsComponents {
    public Map<Body,JointEdge> joints = new HashMap<Body,JointEdge>();
}

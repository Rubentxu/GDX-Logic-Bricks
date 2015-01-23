package com.indignado.logicbricks.utils.builders.joints;

import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Rubentxu
 */
public class JointBuilder {
    private final World world;
    private DistanceJointBuilder distanceJointBuilder;
    private FrictionJointBuilder frictionJointBuilder;
    private GearJointBuilder gearJointBuilder;
    private MouseJointBuilder mouseJointBuilder;
    private PrismaticJointBuilder prismaticJointBuilder;
    private PulleyJointBuilder pulleyJointBuilder;
    private RevoluteJointBuilder revoluteJointBuilder;
    private RopeJointBuilder ropeJointBuilder;
    private WeldJointBuilder weldJointBuilder;
    private WheelJointBuilder wheelJointBuilder;


    public JointBuilder(World world) {
        this.world = world;

    }


    public DistanceJointBuilder distanceJoint() {
        if(distanceJointBuilder == null)  distanceJointBuilder = new DistanceJointBuilder(world);
        else distanceJointBuilder.reset();
        return distanceJointBuilder;

    }


    public FrictionJointBuilder frictionJointBuilder() {
        if(frictionJointBuilder == null)  frictionJointBuilder = new FrictionJointBuilder(world);
        else frictionJointBuilder.reset();
        return frictionJointBuilder;

    }


    public GearJointBuilder gearJointBuilder() {
        if(gearJointBuilder == null)  gearJointBuilder = new GearJointBuilder(world);
        else gearJointBuilder.reset();
        return gearJointBuilder;

    }


    public MouseJointBuilder mouseJointBuilder() {
        if(mouseJointBuilder == null)  mouseJointBuilder = new MouseJointBuilder(world);
        else mouseJointBuilder.reset();
        return mouseJointBuilder;

    }


    public PrismaticJointBuilder prismaticJointBuilder() {
        if(prismaticJointBuilder == null)  prismaticJointBuilder = new PrismaticJointBuilder(world);
        else prismaticJointBuilder.reset();
        return prismaticJointBuilder;

    }


    public PulleyJointBuilder pulleyJointBuilder() {
        if(pulleyJointBuilder == null)  pulleyJointBuilder = new PulleyJointBuilder(world);
        else pulleyJointBuilder.reset();
        return pulleyJointBuilder;

    }


    public RevoluteJointBuilder revoluteJointBuilder() {
        if(revoluteJointBuilder == null)  revoluteJointBuilder = new RevoluteJointBuilder(world);
        else revoluteJointBuilder.reset();
        return revoluteJointBuilder;

    }


    public RopeJointBuilder ropeJointBuilder() {
        if(ropeJointBuilder == null)  ropeJointBuilder = new RopeJointBuilder(world);
        else ropeJointBuilder.reset();
        return ropeJointBuilder;

    }


    public WeldJointBuilder weldJointBuilder() {
        if(weldJointBuilder == null)  weldJointBuilder = new WeldJointBuilder(world);
        else weldJointBuilder.reset();
        return weldJointBuilder;

    }


    public WheelJointBuilder wheelJointBuilder() {
        if(wheelJointBuilder == null)  wheelJointBuilder = new WheelJointBuilder(world);
        else wheelJointBuilder.reset();
        return wheelJointBuilder;

    }

}

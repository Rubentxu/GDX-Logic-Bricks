package com.indignado.logicbricks.utils.builders;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;

/**
 * @author Rubentxu.
 */
public class LightBuilder {
    private final RayHandler rayHandler;
    private int raysNum = 5;
    private Color color= new Color();
    private String  lightType;
    private float distance = 1;

    public LightBuilder(RayHandler rayHandler) {
        this.rayHandler = rayHandler;

    }


    public LightBuilder raysNum(int num) {
        this.raysNum = num;
        return this;

    }


    public LightBuilder color(Color color) {
        this.color = color;
        return this;

    }


    public <T extends Light> LightBuilder lightType(String type) {
        this.lightType = type;
        return this;

    }


    public LightBuilder distance(float distance) {
        this.distance =  distance;
        return this;

    }


    public Light build(float x, float y) {
        Light light = null;
        if (lightType.equals("PointLight")) light = new PointLight(rayHandler, raysNum, color, distance, x, y);
        return light;

    }

}

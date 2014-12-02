package com.indignado.logicbricks.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

/**
 * @author Rubentxu.
 */
public class Settings {
    private static final int entityTypePoolMaxSize = 100;
    // Application config
    public static float Width = 30; //30 metres
    public static float Height = 20; // 20 metres
    public static float aspectRatio = Width / Height;
    public static float metresToPixels = 64;
    public static float pixelsToMetres = 1.0f / metresToPixels;
    public static boolean catchBack = true;
    public static boolean catchMenu = true;
    public static Color backgroundColor = Color.BLUE;
    // LogicBricksEngine
    public static int entityTypePoolInitialSize = 10;
    // Physics
    public static Vector2 gravity = new Vector2(0, -9.81f);
    public static boolean doSleep = false;
    public static int velocityIterations = 6;
    public static int positionIterations = 10;
    public static float physicsDeltaTime = 0.01f;

    // Particles
    public static int particlePoolInitialCapacity;
    public static int particlePoolMaxCapacity;

    // Debug
    public static int debugLevel = Logger.INFO;
    public static boolean debug = debugLevel > Logger.ERROR;
    ;
    public static boolean drawBodies = false;
    public static boolean drawJoints = false;
    public static boolean drawABBs = false;
    public static boolean drawInactiveBodies = false;
    public static boolean drawVelocities = false;
    public static boolean drawContacts = false;
    public static boolean drawStage = false;
    public static boolean drawGrid = false;
    public static boolean drawFPS = true;

}

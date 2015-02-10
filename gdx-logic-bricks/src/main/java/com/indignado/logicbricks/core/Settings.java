package com.indignado.logicbricks.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;


/**
 * @author Rubentxu.
 */
public class Settings {

    // Application config
    public static float WIDTH = 30; //30 metres
    public static float HEIGHT = 20; // 20 metres
    public static float ASPECT_RATIO = WIDTH / HEIGHT;
    public static float METRES_TO_PIXELS = 64;
    public static float PIXELS_TO_METRES = 1.0f / METRES_TO_PIXELS;
    public static boolean CATCHBACK = true;
    public static boolean CATCHMENU = true;
    public static Color BACKGROUNDCOLOR = Color.BLUE;
    // LogicBricksEngine
    public static int ENTITY_POOL_SIZE = 10;
    private static final int ENTITY_POOL_MAX_SIZE = 100;
    // Physics
    public static Vector2 GRAVITY = new Vector2(0, -9.81f);
    public static int BOX2D_VELOCITY_ITERATIONS = 6;
    public static int BOX2D_POSITION_ITERATIONS = 10;

    // STEPS
    public static float FIXED_TIME_STEP = 1.0f / 60.0f;
    public static int MAX_STEPS = 5;


    // Particles
    public static int PARTICLE_POOL_CAPACITY;
    public static int PARTICLE_POOL_MAX_CAPACITY;

    // Debug
    public static int DEBUG_LEVEL = Logger.INFO;
    public static boolean DEBUG = DEBUG_LEVEL > Logger.ERROR;
    public static boolean TESTING = false;
    public static Array<String> DEBUG_TAGS = new Array<>();
    public static String DEBUG_ENTITY;
    public static boolean DRAW_BOX2D_BODIES = false;
    public static boolean DRAW_BOX2D_JOINTS = false;
    public static boolean DRAW_BOX2D_ABBs = false;
    public static boolean DRAW_BOX2D_INACTIVE_BODIES = false;
    public static boolean DRAW_BOX2D_VELOCITIES = false;
    public static boolean DRAW_BOX2D_CONTACTS = false;
    public static boolean DRAW_STAGE = false;
    public static boolean DRAW_GRID = false;
    public static boolean DRAW_FPS = true;
    public static float drawFPSPosX = 0;
    public static float drawFPSPosY = 0;

    // DraggableBody
    public static boolean DRAGGABLE_BOX2D_BODIES = false;
    public static float DRAGGABLE_BOX2D_MAX_FORCE = 500;

}

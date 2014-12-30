package com.indignado.logicbricks.systems.sensors.base;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.actuators.ActuatorComponent;
import com.indignado.logicbricks.components.sensors.SensorComponent;
import com.indignado.logicbricks.core.CategoryBitsManager;
import com.indignado.logicbricks.core.LogicBrick.BrickMode;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.core.sensors.Sensor.Pulse;
import com.indignado.logicbricks.systems.StateSystem;
import com.indignado.logicbricks.systems.actuators.ActuatorSystem;
import com.indignado.logicbricks.systems.sensors.SensorSystem;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import org.junit.*;

import static org.junit.Assert.*;


/**
 * @author Rubentxu
 */
public abstract class BaseSensorSystemTest<S extends Sensor, SS extends SensorSystem> {
    protected static LogicBricksEngine engine;
    protected S sensor;
    protected SS sensorSystem;
    protected static EntityBuilder entityBuilder;
    protected Entity player;


    public BaseSensorSystemTest() {
        Settings.debugLevel = Logger.DEBUG;
        Settings.debugTags.add("sensorSystem");
        engine = new LogicBricksEngine();
        entityBuilder = new EntityBuilder(engine);
    }


    @BeforeClass
    public static void beforeClass() {


    }


    @AfterClass
    public static void afterClass() {
        engine = null;
        entityBuilder = null;

    }


    @Before
    public void baseSetup() {
        setup();
        createContext();

    }


    @After
    public void baseTearDown() {
        tearDown();
       /// engine.removeAllEntities();
        player = null;
        sensor = null;

    }

    protected abstract void setup();

    protected abstract void tearDown();

    protected abstract void createContext();


}

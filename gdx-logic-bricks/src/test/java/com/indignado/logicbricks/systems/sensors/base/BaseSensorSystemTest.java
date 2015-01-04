package com.indignado.logicbricks.systems.sensors.base;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Logger;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.systems.sensors.SensorSystem;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;


/**
 * @author Rubentxu
 */
public abstract class BaseSensorSystemTest<S extends Sensor, SS extends SensorSystem> {
    protected static LogicBricksEngine engine;
    protected static EntityBuilder entityBuilder;
    protected S sensor;
    protected SS sensorSystem;
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
        createContext();

    }


    @After
    public void baseTearDown() {
        tearDown();
        /// engine.removeAllEntities();
        player = null;
        sensor = null;

    }


    protected abstract void tearDown();

    protected abstract void createContext();


}

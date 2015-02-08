package com.indignado.logicbricks.systems.sensors.base;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.badlogic.gdx.utils.Logger;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.bricks.base.BaseTest;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.systems.sensors.SensorSystem;
import org.junit.After;
import org.junit.Before;


/**
 * @author Rubentxu
 */
public abstract class BaseSensorSystemTest<S extends Sensor, SS extends SensorSystem> extends BaseTest {
    protected S sensor;
    protected SS sensorSystem;
    protected Entity player;
    protected Game game;


    public BaseSensorSystemTest() {
        GdxNativesLoader.load();
        Settings.debugLevel = Logger.DEBUG;
        Settings.debugTags.add("sensorSystem");
        this.game = new Game();


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

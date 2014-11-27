package com.indignado.functional.test.levels;

import com.indignado.functional.test.FlyingDartCollisionRule;
import com.indignado.functional.test.entities.Dart;
import com.indignado.logicbricks.core.LevelCreator;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.systems.sensors.CollisionSensorSystem;

/**
 * @author Rubentxu.
 */
public class FlyingDartLevel implements LevelCreator {

    @Override
    public void createLevel(World world) {
        FlyingDartCollisionRule flyingDartCollisionRule = new FlyingDartCollisionRule();
        CollisionSensorSystem collisionSensorSystem = world.;
        collisionSensorSystem.addCollisionRule(flyingDartCollisionRule);
        engine.addSystem(collisionSensorSystem);
        Dart dart = world.obtainEntity(Dart.class);
        dart.init(13,3,0);

    }

}

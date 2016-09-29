package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ReflectionPool;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.config.GameContext;
import com.indignado.logicbricks.config.Settings;
import com.indignado.logicbricks.core.data.Data;
import com.indignado.logicbricks.core.data.LogicBrick;
import com.indignado.logicbricks.systems.LogicBrickSystem;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu.
 */
public class LogicBricksEngine extends PooledEngine {
    private static Long IDS = 0L;
    private String tag = LogicBricksEngine.class.getSimpleName();
    private GameContext context;
    private ObjectMap<Long, Entity> idEntities;
    private ObjectMap<String, Array<Entity>> tagEntities;
    private DataPools dataPools;
    private InputMultiplexer inputs;
    private Array<ContactListener> contactSystems;
    private ObjectMap<Class<? extends LogicBrick>, BricksClasses> bricksClasses;
    private ObjectMap<Class<? extends Component>, Class<? extends LogicBrickSystem>[]> systemClasses;


    public LogicBricksEngine(GameContext context) {
        this(10, 100, 10, 100, 50, 200, context);

    }


    public LogicBricksEngine(int entityPoolInitialSize, int entityPoolMaxSize, int componentPoolInitialSize,
                             int componentPoolMaxSize, int bricksPoolInitialSize, int brickPoolMaxSize, GameContext context) {
        super(entityPoolInitialSize, entityPoolMaxSize, componentPoolInitialSize, componentPoolMaxSize);
        idEntities = new ObjectMap<Long, Entity>();
        tagEntities = new ObjectMap<String, Array<Entity>>();
        dataPools = new DataPools(bricksPoolInitialSize, brickPoolMaxSize);
        inputs = new InputMultiplexer();
        contactSystems = new Array<>();
        this.context = context;

        systemClasses = new ObjectMap<>();
        bricksClasses = new ObjectMap<>();

    }


    @Override
    protected void removeEntityInternal(Entity entity) {
        IdentityComponent identity = getComponent(entity, IdentityComponent.class, false);
        if (Settings.DEBUG_ENTITY != null) tag = String.format("%s::%s:", this.getClass().getSimpleName(), identity.tag);
        Log.debug(tag, "LogicBricksEngine");
        idEntities.remove(identity.uuid);
        tagEntities.get(identity.tag).removeValue(entity, false);
        super.removeEntityInternal(entity);

    }


    @Override
    public void addSystem(EntitySystem system) {
        super.addSystem(system);
        if (EntityListener.class.isInstance(system)) {
            this.addEntityListener((EntityListener) system);

        }
        if (InputProcessor.class.isInstance(system)) {
            inputs.addProcessor((InputProcessor) system);

        }
        if (ContactListener.class.isInstance(system)) {
            contactSystems.add((ContactListener) system);

        }

    }


    @Override
    public void addEntity(Entity entity) {
        super.addEntity(entity);
        IdentityComponent identity = getComponent(entity, IdentityComponent.class, true);
        idEntities.put(identity.uuid, entity);
        configEntity(entity);
        Log.debug(tag, "AddEntity %d", identity.uuid);

    }


    public void update(float deltaTime) {
        super.update(deltaTime);


    }


    @Override
    public void clearPools() {
        super.clearPools();
        dataPools.clear();

    }


    private void configEntity(Entity entity) {
        IdentityComponent identity = getComponent(entity, IdentityComponent.class, true);
        identity.uuid = IDS++;
        if (Settings.DEBUG_ENTITY != null) tag = String.format("%s::%s:", this.getClass().getSimpleName(), identity.tag);
        if (!tagEntities.containsKey(identity.tag)) {
            tagEntities.put(identity.tag, new Array<Entity>());

        }
        tagEntities.get(identity.tag).add(entity);

        RigidBodiesComponents rigidBodies = getComponent(entity, RigidBodiesComponents.class, false);

        if (rigidBodies != null) {
            for (Body rigidBody : rigidBodies.rigidBodies) {
                rigidBody.setUserData(entity);
                for (Fixture fixture : rigidBody.getFixtureList()) {
                    Filter filter = new Filter();
                    filter.categoryBits = identity.category;
                    filter.maskBits = identity.collisionMask;
                    filter.groupIndex = identity.group;
                    fixture.setFilterData(filter);
                }
            }
        }

    }


    public <C extends Component> C getComponent(Entity entity, Class<C> clazzComponent, boolean create) {
        C comp = null;
        comp = entity.getComponent(clazzComponent);
        if (comp == null && create) {
            comp = createComponent(clazzComponent);
            entity.add(comp);
        }
        return comp;

    }


    public Entity getEntity(long uuid) {
        return idEntities.get(uuid);

    }


    public Array<Entity> getEntities(String tag) {
        if (tagEntities.containsKey(tag)) return tagEntities.get(tag);
        return null;

    }


    public <D extends Data> D createData(Class<D> dataType) {
        return (D) dataPools.obtain(dataType);

    }

    public InputMultiplexer getInputs() {
        return inputs;
    }

    public Array<ContactListener> getContactSystems() {
        return contactSystems;
    }


    public GameContext getGameContext() {
        return context;

    }


    public void registerBricksClasses(Class <? extends LogicBrick> brick, Class<? extends Component> component,
                                               Class<? extends LogicBrickSystem> system) {
        bricksClasses.put(brick, new BricksClasses(component, system));

    }


    public void registerEngineClasses(Class<? extends Component> component, Class<? extends LogicBrickSystem> ... system) {
        systemClasses.put(component, system);

    }


    public <L extends LogicBrick> BricksClasses getBricksClasses(Class<L> clazz) {
        return bricksClasses.get(clazz);

    }


    public Class<? extends LogicBrickSystem>[] getSystemClass(Class<? extends Component> clazz) {
        return systemClasses.get(clazz);

    }


    private class DataPools<D extends Data> {
        private ObjectMap<Class<D>, ReflectionPool> pools;
        private int initialSize;
        private int maxSize;


        public DataPools(int initialSize, int maxSize) {
            this.pools = new ObjectMap<Class<D>, ReflectionPool>();
            this.initialSize = 0;
            this.maxSize = 0;

        }


        public D obtain(Class<D> type) {
            ReflectionPool pool = pools.get(type);
            if (pool == null) {
                pool = new ReflectionPool(type, initialSize, maxSize);
                pools.put(type, pool);
            }
            return (D) pool.obtain();

        }


        public void free(D object) {
            if (object == null) {
                throw new IllegalArgumentException("object cannot be null.");
            }
            ReflectionPool pool = pools.get((Class<D>) object.getClass());
            if (pool == null) {
                return; // Ignore freeing an object that was never retained.
            }
            pool.free(object);

        }


        public void freeAll(Array<D> objects) {
            if (objects == null) throw new IllegalArgumentException("objects cannot be null.");

            for (int i = 0, n = objects.size; i < n; i++) {
                D object = objects.get(i);
                if (object == null) continue;
                free(object);
            }

        }


        public void clear() {
            for (Pool pool : pools.values()) {
                pool.clear();
            }

        }

    }

}

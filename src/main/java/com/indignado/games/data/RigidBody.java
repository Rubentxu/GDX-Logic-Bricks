package com.indignado.games.data;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Rubentxu.
 */
public class RigidBody extends Body {
    /**
     * Constructs a new body with the given address
     *
     * @param world the world
     * @param addr  the address
     */
    protected RigidBody(World world, long addr) {
        super(world, addr);
    }

}

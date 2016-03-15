package com.asteroids.entity.spawner;

import com.asteroids.entity.Entity;
import com.asteroids.math.Vector2d;

public class Spawner extends Entity {

    public enum Type {
        PARTICLE,
        PARTICLE_PU
    }

    private Type type;

    public Spawner(Vector2d pos, Type type) {
        this.pos.set(pos);
        this.type = type;

    }
}

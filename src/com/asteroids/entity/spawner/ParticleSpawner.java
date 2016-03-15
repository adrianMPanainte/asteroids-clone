package com.asteroids.entity.spawner;

import com.asteroids.entity.particle.Particle;
import com.asteroids.graphics.Sprite;
import com.asteroids.math.Vector2d;

public class ParticleSpawner extends Spawner {

    private int life;
    private Sprite sprite;

    public ParticleSpawner(Vector2d pos, int life, int amount, Type type) {
        super(pos, type);
        this.life = life;
        switch (type) {
            case PARTICLE:
                sprite = Sprite.particle_0;
                break;
            case PARTICLE_PU:
                sprite = Sprite.particle_1;
                break;
        }
        for(int i = 0; i < amount; i ++) {
            level.add(new Particle(pos, life, sprite));

        }
    }
}

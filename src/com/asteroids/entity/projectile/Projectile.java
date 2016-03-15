package com.asteroids.entity.projectile;

import com.asteroids.entity.Entity;
import com.asteroids.graphics.CollisionMask;
import com.asteroids.graphics.Sprite;
import com.asteroids.math.Vector2d;


public abstract class Projectile extends Entity {

    protected final Vector2d origin;
    protected double angle;
    protected Sprite sprite;
    protected Vector2d movement;
    public CollisionMask collisionMask;


    public Projectile(Vector2d pos, double dir) {
        origin = new Vector2d();
        movement = new Vector2d();
        origin.set(pos);
        angle = dir;
        this.pos.set(pos);

    }

    protected void move() {

    }

    public Vector2d getPos() {
        return pos;
    }


}

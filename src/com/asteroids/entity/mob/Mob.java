package com.asteroids.entity.mob;

import com.asteroids.entity.Entity;
import com.asteroids.entity.projectile.BulletProjectile;
import com.asteroids.entity.projectile.Projectile;
import com.asteroids.graphics.Screen;
import com.asteroids.graphics.Sprite;
import com.asteroids.math.Vector2d;

import java.util.Random;


public abstract class Mob extends Entity {

    protected double dir = 0;
    protected boolean moving = false;



    public void move(Vector2d movement, double dir) {

        this.dir = dir;
        pos.add(movement);

    }

    protected void shoot(Vector2d pos, double dir) {
        Projectile p = new BulletProjectile(pos, dir);
        level.add(p);
    }

    protected void generateAsteroid(Vector2d pos) {

        double angle = Math.random()*Math.PI*2;

        Vector2d randomPos = new Vector2d(Math.cos(angle)*500, Math.sin(angle)*500);
        Random rand = new Random();
        Asteroid a = new Asteroid(pos.sum(randomPos), - angle, rand.nextInt(2));
        level.add(a);
    }

    public void update() {

    }

    private boolean collision() {
        return false;
    }

    public void render(Screen screen) {

    }
}

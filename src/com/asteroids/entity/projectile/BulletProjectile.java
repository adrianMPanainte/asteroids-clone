package com.asteroids.entity.projectile;

import com.asteroids.graphics.CollisionMask;
import com.asteroids.graphics.Screen;
import com.asteroids.graphics.Sprite;
import com.asteroids.level.Level;
import com.asteroids.math.Vector2d;
import com.asteroids.math.Vector2i;


public class BulletProjectile extends Projectile {

    public static int FIRE_RATE = 15;
    private static float powerUpTime = 0;
    private static float speedUpTime = 0;
    private double dir;
    private static boolean powerUp = false, speedUp = false;
    protected static double speed, range, damage;
    private int spriteType = 0;

    public BulletProjectile(Vector2d pos, double dir) {
        super(pos, dir);
        speed = 10.0;
        range = 1000;
        radius = 4;
        sprite = Sprite.rotate(Sprite.bullet, dir);
        this.dir = dir;
        movement.set(Math.cos(angle) * speed, Math.sin(angle) * speed);
        collisionMask = new CollisionMask(Sprite.rotate(CollisionMask.bulletCollider, dir));
    }

    public void update() {
        move();

        if(powerUp) {
            if (spriteType == 0) {
                sprite = Sprite.rotate(Sprite.powerUp_bullet, dir);
                spriteType = 1;
            }
        }
        else if(spriteType == 1) {
            sprite = Sprite.rotate(Sprite.bullet, dir);
            spriteType = 0;
        }

    }



    protected void move() {
        pos.add(movement);
        if(Vector2d.distance(pos, origin) > range) remove();
    }

    public void render(Screen screen) {
        screen.renderProjectile((int)pos.getX(), (int)pos.getY(), sprite);
        if(Level.isDebugging)
        {
            screen.renderPlayer((int)pos.getX()- Sprite.bullet.SIZE/2, (int)pos.getY() - Sprite.bullet.SIZE/2, Sprite.center);
            screen.renderPlayer((int)pos.getX()- Sprite.bullet.SIZE/2, (int)pos.getY()- Sprite.bullet.SIZE/2, Sprite.box32);
            screen.renderPlayer((int) pos.getX(), (int) pos.getY(), collisionMask.getCollisionSprite());
            for(Vector2i point : collisionMask.collisionPoints) {
                screen.renderPlayer(-point.getX() + (int)pos.getX(), -point.getY() + (int)pos.getY(), Sprite.center);
            }
        }
    }

    public static boolean isPowerUp() {
        return powerUp;
    }

    public static void setFireRate(int fireRate) {
        FIRE_RATE = fireRate;
    }

    public static void setPowerUpTime(float powerUpTime) {
        BulletProjectile.powerUpTime = powerUpTime;
    }

    public static void setSpeedUpTime(float speedUpTime) {
        BulletProjectile.speedUpTime = speedUpTime;
    }

    public static void setPowerUp(boolean powerUp) {
        BulletProjectile.powerUp = powerUp;
    }

    public static void setSpeedUp(boolean speedUp) {
        BulletProjectile.speedUp = speedUp;
    }

    public static void setDamage(double damage) {
        BulletProjectile.damage = damage;
    }

    public static float getSpeedUpTime() {
        return speedUpTime;
    }

    public static float getPowerUpTime() {
        return powerUpTime;
    }

    public static double getDamage() {
        return damage;
    }
}

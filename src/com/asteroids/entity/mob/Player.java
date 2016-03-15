package com.asteroids.entity.mob;

import com.asteroids.entity.Entity;
import com.asteroids.entity.projectile.BulletProjectile;
import com.asteroids.entity.projectile.Projectile;
import com.asteroids.graphics.CollisionMask;
import com.asteroids.graphics.Screen;
import com.asteroids.graphics.Sprite;
import com.asteroids.input.Keyboard;
import com.asteroids.level.Level;
import com.asteroids.math.Vector2d;
import com.asteroids.math.Vector2i;

public class Player extends Mob {

    public Keyboard input;
    private int anim;
    private double angle;
    private Sprite sprite, rotatedSprite;
    public CollisionMask collisionMask;

    private int fireRate = BulletProjectile.FIRE_RATE;
    private int spawnRate = Asteroid.SPAWN_RATE;

    public static Vector2d pos = new Vector2d();

    private double speed = 0.07;
    private Vector2d movement;
    private Vector2d accel;

    public Player(Keyboard input) {

        this.input = input;
        this.angle = -90;
        this.sprite = Sprite.player;
        this.rotatedSprite = sprite;

    }

    public Player(Vector2d position, Keyboard input) {
        pos.set(position);
        setPos(position);
        this.movement = new Vector2d();
        this.accel = new Vector2d();
        this.input = input;
        this.angle = -90;
        this.sprite = Sprite.player;
        this.rotatedSprite = Sprite.rotate(sprite, Math.toRadians(angle));
        this.collisionMask = new CollisionMask(Sprite.rotate(CollisionMask.playerCollider, Math.toRadians(angle)));
    }


    public void update() {

        if(anim < 0.3 * 60) anim ++;
        else anim = 0;
        if(fireRate > 0) fireRate --;
        if(spawnRate > 0) spawnRate --;

        if (anim < 0.3 * 60 / 2) {
            sprite = Sprite.player_2;
        } else {
            sprite = Sprite.player_3;
        }

        if(input.up) {

            accel.set(Math.cos(Math.toRadians(angle)) * speed, Math.sin(Math.toRadians(angle)) * speed);
            if(movement.sum(accel).length() < 5)
                movement.add(accel);
            movement.set(movement.mul(0.99));

            if (anim < 0.3 * 60 / 2) {
                sprite = Sprite.player_2_accel;
            } else {
                sprite = Sprite.player_3_accel;
            }

        }

        if(input.left) {
            angle -= 4;
            if(angle <= -360) angle = 0;

        }
        if(input.right){
            angle += 4;
            if(angle >= 360) angle = 0;
        }

        if(!movement.equals(Vector2d.ZERO)) {
            move(movement, angle);
            pos.add(movement);
        }

        if(input.space && fireRate <= 0) {
            shoot(pos.dif(Sprite.bullet.SIZE/2), Math.toRadians(angle));
            fireRate = BulletProjectile.FIRE_RATE;
        }

        if(input.f1) {
            Level.isDebugging = !Level.isDebugging;
        }

        if(spawnRate <= 0) {
            generateAsteroid(pos);
            spawnRate = Asteroid.SPAWN_RATE;
        }

        rotateSprites();
        collisionMask.generateCollisionPoints();
        clear();
        handlePowerUps();
    }

    private void rotateSprites() {
        rotatedSprite = Sprite.rotate(sprite, Math.toRadians(angle));
        collisionMask.setCollisionSprite(Sprite.rotate(CollisionMask.playerCollider, Math.toRadians(angle)));
    }

    private void clear() {
        for(int i = 0; i < level.projectiles.size(); i ++) {
            Projectile p = level.projectiles.get(i);
            if(p.isRemoved()) level.projectiles.remove(i);
        }

        for(int i = 0; i < level.asteroids.size(); i ++) {
            Entity a = level.asteroids.get(i);
            if(a.isRemoved()) level.asteroids.remove(i);
        }
    }

    private void handlePowerUps() {
        if(BulletProjectile.getPowerUpTime() > 0)
            BulletProjectile.setPowerUpTime(BulletProjectile.getPowerUpTime() - 1);
        if(BulletProjectile.getSpeedUpTime() > 0)
            BulletProjectile.setSpeedUpTime(BulletProjectile.getSpeedUpTime() - 1);


        if(BulletProjectile.getPowerUpTime() <= 0)
        {
            BulletProjectile.setDamage(50);
            BulletProjectile.setPowerUp(false);
        }

        if(BulletProjectile.getSpeedUpTime() <= 0)
        {
            BulletProjectile.setFireRate(15);
            BulletProjectile.setSpeedUp(false);
        }
    }

    public static void powerUp(float time) {
        BulletProjectile.setPowerUpTime(time * 60);
        BulletProjectile.setDamage(110);
        BulletProjectile.setPowerUp(true);

    }

    public static void speedUp(float time) {
        BulletProjectile.setSpeedUpTime(time * 60);
        BulletProjectile.setFireRate(8);
        BulletProjectile.setSpeedUp(true);
    }

    public void reset(Vector2d position) {
        pos.set(position);
        this.angle = -90;
        this.movement.set(Vector2d.ZERO);
        this.rotatedSprite = sprite;
    }

    public void render(Screen screen) {

        sprite = Sprite.player;

        screen.renderPlayer((int) pos.getX() - Sprite.player.SIZE / 2, (int) pos.getY() - Sprite.player.SIZE / 2, rotatedSprite);
        if (Level.isDebugging) {
            screen.renderSprite((int) pos.getX() - Sprite.player.SIZE / 2, (int) pos.getY() - Sprite.player.SIZE / 2, Sprite.box64, true);
            screen.renderSprite((int) pos.getX() - Sprite.player.SIZE / 2, (int) pos.getY() - Sprite.player.SIZE / 2, collisionMask.getCollisionSprite(), true);
            for (Vector2i point : collisionMask.collisionPoints) {
                screen.renderSprite(point.getX() + (int) pos.getX() - collisionMask.getCollisionSprite().SIZE + 16, point.getY() + (int) pos.getY() - collisionMask.getCollisionSprite().SIZE + 16, Sprite.center, true);
            }
        }
    }
    public static Vector2d getPos() {
        return pos;
    }
}

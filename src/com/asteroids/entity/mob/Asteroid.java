package com.asteroids.entity.mob;

import com.asteroids.Game;
import com.asteroids.graphics.CollisionMask;
import com.asteroids.graphics.Screen;
import com.asteroids.graphics.Sprite;
import com.asteroids.level.Level;
import com.asteroids.math.Vector2d;
import com.asteroids.math.Vector2i;

public class Asteroid extends Mob {

    public static final int SPAWN_RATE = 5, DESPAWN_TIME = 500;
    private final double ROT_SPEED;

    protected Sprite sprite, rotatedSprite, collisionSprite;

    private int type;
    public int health = 100;
    private double speed = Math.random() * 3;
    private double rot = 0;
    private double rotSpeed;
    private int despawnTime;

    public CollisionMask collisionMask;

    private Vector2d movement = new Vector2d();

    public Asteroid(Vector2d pos, double dir, int type) {
        this.pos.set(pos);
        this.dir = dir;

        radius = 15;
        this.type = type;

        switch (type){
            case 0:
                collisionMask = new CollisionMask(CollisionMask.asteroidCollider);
                collisionSprite = CollisionMask.asteroidCollider;
                this.sprite = Sprite.asteroid;
                this.rotatedSprite = Sprite.asteroid;
                break;
            case 1:
                collisionMask = new CollisionMask(CollisionMask.asteroid2Collider);
                collisionSprite = CollisionMask.asteroid2Collider;
                this.sprite = Sprite.asteroid2;
                this.rotatedSprite = Sprite.asteroid2;
                break;
        }

        this.movement.set(Math.cos(dir) * speed, Math.sin(dir) * speed);
        ROT_SPEED = Math.random() * 5;
        rotSpeed = ROT_SPEED;
        despawnTime = DESPAWN_TIME;
        collisionMask.setCollisionSprite(Sprite.rotate(collisionSprite, Math.toRadians(rot)));

    }

    public void update() {
        if (!Level.isDebugging) move();

        if(rotSpeed > 0) rotSpeed --;
        if(rotSpeed <= 0) {
            rotSpeed = ROT_SPEED;
            rot += 2;
            rotatedSprite = Sprite.rotate(sprite, Math.toRadians(rot));
            collisionMask.setCollisionSprite(Sprite.rotate(collisionSprite, Math.toRadians(rot)));
        }
        double distanceToPlayer = 51;
        if(!level.isDebugging) distanceToPlayer = Vector2d.distance(new Vector2d().set(Player.getPos()).dif(Sprite.player.SIZE/2), pos);

        checkPlayerCollision(distanceToPlayer);

        if(despawnTime > 0) despawnTime --;
        if(despawnTime <= 0) {
            despawnTime = DESPAWN_TIME;

            if(distanceToPlayer > 400)
                remove();

        }

    }

    public void checkPlayerCollision(double distance) {
        if(distance <= 50) {
            Game.player.collisionMask.generateCollisionPoints();
            collisionMask.generateCollisionPoints();
            for(Vector2i posA : Game.player.collisionMask.collisionPoints) {
                for(Vector2i posB : collisionMask.collisionPoints){
                    if(new Vector2i(posA.getX() + (int)Game.player.pos.getX() - Game.player.collisionMask.getCollisionSprite().SIZE + 16, posA.getY() + (int)Game.player.pos.getY() - Game.player.collisionMask.getCollisionSprite().SIZE + 16).equals(new Vector2i(posB.getX() + (int)pos.getX() - collisionMask.getCollisionSprite().SIZE/2, posB.getY() + (int)pos.getY() - collisionMask.getCollisionSprite().SIZE/2))) {
                        level.endLevel();

                    }
                }
            }

        }
    }

    public void render(Screen screen) {
        screen.renderProjectile((int)pos.getX(), (int)pos.getY(), rotatedSprite);
        if(Level.isDebugging)
        {
            screen.renderPlayer((int)pos.getX(), (int)pos.getY(), Sprite.center);
            screen.renderPlayer((int)pos.getX(), (int)pos.getY(), Sprite.box32);
            screen.renderPlayer((int) pos.getX(), (int) pos.getY(), collisionMask.getCollisionSprite());
            for(Vector2i point : collisionMask.collisionPoints) {
                screen.renderPlayer(point.getX() + (int)pos.getX() - collisionMask.getCollisionSprite().SIZE/2, point.getY() + (int)pos.getY() - collisionMask.getCollisionSprite().SIZE/2, Sprite.center);
            }
        }

    }

    protected void move() {
        pos.add(movement);
    }

    public Vector2d getPos() {
        return pos;
    }
}

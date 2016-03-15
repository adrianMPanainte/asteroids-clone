package com.asteroids.entity.mob;

import com.asteroids.Game;
import com.asteroids.entity.Entity;
import com.asteroids.graphics.Animation;
import com.asteroids.graphics.CollisionMask;
import com.asteroids.graphics.Screen;
import com.asteroids.graphics.Sprite;
import com.asteroids.level.Level;
import com.asteroids.math.Vector2d;
import com.asteroids.math.Vector2i;


public class PowerUp extends Entity {

    public CollisionMask collisionMask;
    private int life = 10 * 60;
    private Type type;

    public enum Type {
        POWER,
        SPEED
    }

    public Animation animation;

    public PowerUp(Vector2d pos, Type type) {
        this.pos.set(pos);
        this.type = type;
        switch (type){
            case POWER:
                this.animation = new Animation(pos, (float)0.4, Sprite.powerUp1, true);
                break;
            case SPEED:
                this.animation = new Animation(pos, (float)0.4, Sprite.powerUp2, true);
        }
        collisionMask = new CollisionMask(CollisionMask.powerUpCollider);
        collisionMask.generateCollisionPoints();
    }

    public void update() {
        if(life > 0) life --;
        if(life <= 0)
        {
            remove();
            animation.remove();
        }

        double distanceToPlayer = Vector2d.distance(new Vector2d().set(Player.getPos()).dif(Sprite.player.SIZE/2), pos);
        checkPlayerCollision(distanceToPlayer);
    }

    public void checkPlayerCollision(double distance) {
        if(distance <= 50) {
            Game.player.collisionMask.generateCollisionPoints();
            collisionMask.generateCollisionPoints();
            for(Vector2i posA : Game.player.collisionMask.collisionPoints) {
                for(Vector2i posB : collisionMask.collisionPoints){
                    if(new Vector2i(posA.getX() + (int)Player.pos.getX() - Game.player.collisionMask.getCollisionSprite().SIZE + 16, posA.getY() + (int)Player.pos.getY() - Game.player.collisionMask.getCollisionSprite().SIZE + 16).equals(new Vector2i(posB.getX() + (int)pos.getX() - collisionMask.getCollisionSprite().SIZE/2, posB.getY() + (int)pos.getY() - collisionMask.getCollisionSprite().SIZE/2))) {
                        {
                            if(type == Type.POWER) Player.powerUp(5);
                            else if(type == Type.SPEED) Player.speedUp(5);
                            this.animation.remove();
                            this.remove();
                        }

                    }
                }
            }

        }
    }
    public void render(Screen screen) {
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
}

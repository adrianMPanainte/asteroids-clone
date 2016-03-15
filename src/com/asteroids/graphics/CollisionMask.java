package com.asteroids.graphics;

import com.asteroids.math.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class CollisionMask {

    public static Sprite playerCollider = new Sprite(64, 0, 1, SpriteSheet.colliders);
    public static Sprite asteroidCollider = new Sprite(32, 1, 1, SpriteSheet.colliders);
    public static Sprite asteroid2Collider = new Sprite(32, 2, 1, SpriteSheet.colliders);
    public static Sprite bulletCollider = new Sprite(16, 1, 0, SpriteSheet.colliders);
    public static Sprite powerUpCollider = new Sprite(32, 3, 0, SpriteSheet.colliders);

    public List<Vector2i> collisionPoints = new ArrayList<>();

    private Sprite collisionSprite;

    public CollisionMask(Sprite sprite) {
        collisionSprite = sprite;
    }

    public void generateCollisionPoints() {
        collisionPoints.clear();
        for(int y = 0; y < collisionSprite.SIZE; y ++)
            for(int x = 0; x < collisionSprite.SIZE; x ++) {
                if(collisionSprite.pixels [x + y * collisionSprite.SIZE] == 0xff00ff00) collisionPoints.add(new Vector2i(x, y));
            }
    }

    public Sprite getCollisionSprite() {
        return collisionSprite;
    }

    public void setCollisionSprite(Sprite collisionSprite) {
        this.collisionSprite = collisionSprite;
    }
}



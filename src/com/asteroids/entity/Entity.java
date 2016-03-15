package com.asteroids.entity;

import com.asteroids.graphics.Screen;
import com.asteroids.level.Level;
import com.asteroids.math.Vector2d;

import java.util.Random;

public abstract class Entity {

    public Vector2d pos = new Vector2d();
    private boolean removed = false;
    public int radius;
    protected static Level level;
    protected final Random random = new Random();


    public void update() {

    }

    public void render(Screen screen) {

    }

    public static void init(Level l) {
        level = l;
    }

    public void remove() {
        //sterge din nivel
        removed = true;
    }

    public void setPos(Vector2d pos) {
        this.pos.set(pos);
    }
    public boolean isRemoved() {
        return removed;
    }
}

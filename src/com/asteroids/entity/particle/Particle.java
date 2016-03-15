package com.asteroids.entity.particle;

import com.asteroids.entity.Entity;
import com.asteroids.graphics.Screen;
import com.asteroids.graphics.Sprite;
import com.asteroids.level.Level;
import com.asteroids.math.Vector2d;


public class Particle extends Entity {

    private Sprite sprite;

    private int life;
    private int time;

    protected Vector2d movement = new Vector2d();

    public Particle(Vector2d pos, int life, Sprite sprite) {
        this.pos.set(pos);
        this.life = life + (random.nextInt(20) - 10);
        this.sprite = sprite;

        this.movement.set(random.nextGaussian(), random.nextGaussian());
    }


    public void update() {
        time ++;
        if(time > 7400) time = 0;
        if(time > life)
            remove();

        this.pos.add(movement);
    }

    public void render(Screen screen) {
        screen.renderSprite((int)pos.getX(), (int) pos.getY(), sprite, true);
        if( Level.isDebugging)screen.renderSprite((int)pos.getX(), (int) pos.getY(), Sprite.box32, true);
    }
}

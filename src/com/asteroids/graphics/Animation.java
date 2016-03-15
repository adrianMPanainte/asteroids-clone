package com.asteroids.graphics;

import com.asteroids.entity.Entity;
import com.asteroids.math.Vector2d;

import java.util.ArrayList;
import java.util.List;


public class Animation extends Entity {

    private List<Sprite> frames = new ArrayList<>();
    private Sprite sprite;
    private float time, delta;
    private int currentFrame;
    private boolean looping;

    public Animation(Vector2d pos, float time, Sprite[] frame, boolean loop) {
        this.pos.set(pos);
        this.time = time/frame.length * 60; //timpul pentru fiecare frame este scazut de 60 de ori pe secunda
        delta = this.time;
        for(Sprite s : frame)
            frames.add(s);
        looping = loop;
        sprite = frame[0];
        currentFrame = 0;
    }

    public void update() {

        if (delta <= 0) {
            currentFrame++;
            if (currentFrame < frames.size()) {
                this.sprite = frames.get(currentFrame);
                delta = time;

            }
        }
        if (delta > 0) {
            delta--;
        }

        if (currentFrame == frames.size())
            if (looping)
            {
                currentFrame = 0;
                this.sprite = frames.get(0);
                delta = time;
            }
            else this.remove();
    }

    public void render(Screen screen) {
        screen.renderSprite((int) pos.getX(), (int) pos.getY(), sprite, true);
    }

}

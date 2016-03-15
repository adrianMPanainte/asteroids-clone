package com.asteroids.level;

import com.asteroids.graphics.Screen;
import com.asteroids.graphics.Sprite;


public class Tile {

    public int x, y;
    public Sprite sprite;

    public static Tile space = new SpaceTile(Sprite.space);
    public static Tile voidTile = new VoidTile(Sprite.voidSprite);
    public static Tile space2 = new SpaceTile(Sprite.space2);
    public static Tile space3 = new SpaceTile(Sprite.space3);

    public Tile(Sprite sprite) {
        this.sprite = sprite;
    }

    public void render(int x, int y, Screen screen) {

    }

    public boolean solid() {
        return false;
    }

}

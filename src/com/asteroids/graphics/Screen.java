package com.asteroids.graphics;

import com.asteroids.level.Tile;

import java.util.Random;

public class Screen {

    private int width, height;
    public int[] pixels;
    public final int MAP_SIZE = 8;

    public int[] tiles = new int[MAP_SIZE * MAP_SIZE];

    public int xOffset, yOffset;

    private Random random = new Random();

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];

        for(int i = 0; i < MAP_SIZE * MAP_SIZE; i ++) {
            tiles[i] = random.nextInt(0xffffff);
        }
    }

    public void clear(){
        for(int i = 0; i < pixels.length; i ++){
            pixels[i] = 0;
        }
    }

    public void renderTile(int xPos, int yPos, Tile tile) {
        xPos -= xOffset;
        yPos -= yOffset;

        for(int y = 0; y < tile.sprite.SIZE; y ++) {
            int yAbs = yPos + y;
            for(int x = 0; x < tile.sprite.SIZE; x ++) {
                int xAbs = x + xPos;
                if(xAbs < -tile.sprite.SIZE || xAbs >= width || yAbs < 0 || yAbs >= height) break;
                if(xAbs < 0) xAbs = 0;
                int col = tile.sprite.pixels[x + y * tile.sprite.SIZE];
                if(col != 0xFFFF00FF)
                pixels[xAbs + yAbs * width] = col;
            }
        }
    }

    public void renderProjectile(int xPos, int yPos, Sprite sprite) {
        xPos -= xOffset;
        yPos -= yOffset;

        for(int y = 0; y < sprite.SIZE; y ++) {
            int yAbs = yPos + y;
            for(int x = 0; x < sprite.SIZE; x ++) {
                int xAbs = x + xPos;
                if(xAbs < -sprite.SIZE || xAbs >= width || yAbs < 0 || yAbs >= height) break;
                if(xAbs < 0) xAbs = 0;
                int col = sprite.pixels[x + y * sprite.SIZE];
                if(col != 0xFFFF00FF)
                    pixels[xAbs + yAbs * width] = col;
            }
        }
    }

    public void renderSprite(int xPos, int yPos, Sprite sprite, boolean fixed) {

        if(fixed) {
            xPos -= xOffset;
            yPos -= yOffset;
        }

        for(int y = 0; y < sprite.SIZE; y ++) {
            int yAbs = yPos + y;
            for(int x = 0; x < sprite.SIZE; x ++) {
                int xAbs = x + xPos;
                if(xAbs < -sprite.SIZE || xAbs >= width || yAbs < 0 || yAbs >= height) break;
                if(xAbs < 0) xAbs = 0;
                int col = sprite.pixels[x + y * sprite.SIZE];
                if(col != 0xFFFF00FF)
                    pixels[xAbs + yAbs * width] = col;
            }
        }
    }


    public void renderPlayer(int xPos, int yPos, Sprite sprite) {
        xPos -= xOffset;
        yPos -= yOffset;

        for(int y = 0; y < sprite.SIZE; y ++) {
            int yAbs = yPos + y;
            for(int x = 0; x < sprite.SIZE; x ++) {
                int xAbs = x + xPos;
                if(xAbs < -sprite.SIZE || xAbs >= width || yAbs < 0 || yAbs >= height) break;
                if(xAbs < 0) xAbs = 0;

                int col = sprite.pixels[x + y * sprite.SIZE];
                if(col != 0xFFFF00FF)
                    pixels[xAbs + yAbs * width] = col;
            }
        }
    }



    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}

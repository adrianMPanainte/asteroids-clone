package com.asteroids.graphics;

import com.asteroids.Game;

public class Sprite {

    public final int SIZE;
    protected int x, y;
    public int[] pixels;

    protected SpriteSheet sheet;

    public static Sprite space = new Sprite(16, 0, 0, SpriteSheet.tiles);
    public static Sprite space2 = new Sprite(16, 0, 1, SpriteSheet.tiles);
    public static Sprite space3 = new Sprite(16, 0, 2, SpriteSheet.tiles);

    public static Sprite bullet = new Sprite(16, 1, 0, SpriteSheet.tiles);
    public static Sprite powerUp_bullet = new Sprite(16, 1, 1, SpriteSheet.tiles);

    public static Sprite voidSprite = new Sprite(16, 0);

    public static Sprite player = (Game.quality == 0) ? new Sprite(32, 1, 0, SpriteSheet.tiles) : new Sprite(64, 2, 4, SpriteSheet.hrTiles);
    public static Sprite player_2 = (Game.quality == 0) ? new Sprite(32, 1, 0, SpriteSheet.tiles) : new Sprite(64, 2, 4, SpriteSheet.hrTiles);
    public static Sprite player_3 = (Game.quality == 0) ? new Sprite(32, 1, 0, SpriteSheet.tiles) : new Sprite(64, 2, 5, SpriteSheet.hrTiles);

    public static Sprite player_2_accel = (Game.quality == 0) ? new Sprite(32, 1, 0, SpriteSheet.tiles) : new Sprite(64, 2, 2, SpriteSheet.hrTiles);
    public static Sprite player_3_accel = (Game.quality == 0) ? new Sprite(32, 1, 0, SpriteSheet.tiles) : new Sprite(64, 2, 3, SpriteSheet.hrTiles);

    public static Sprite center = new Sprite(32, 0, 2, SpriteSheet.tiles);
    public static Sprite box32 = new Sprite(32, 1, 2, SpriteSheet.tiles);
    public static Sprite box64 = new Sprite(64, 0, 2, SpriteSheet.tiles);

    public static Sprite asteroid2 = new Sprite(32, 2, 1, SpriteSheet.tiles);
    public static Sprite asteroid = new Sprite(32, 1, 1, SpriteSheet.tiles);

    public static Sprite particle_0 = new Sprite(2, 0xFFFFFF);
    public static Sprite particle_1 = new Sprite(2, 0xFF6B6B);

    public static Sprite[] powerUp1 = {
            new Sprite(32, 3, 0, SpriteSheet.tiles),
            new Sprite(32, 4, 0, SpriteSheet.tiles)
    };

    public static Sprite[] powerUp2 = {
            new Sprite(32, 3, 1, SpriteSheet.tiles),
            new Sprite(32, 4, 1, SpriteSheet.tiles)
    };

    public static Sprite[] explosion = {
            new Sprite(64, 0, 6, SpriteSheet.hrTiles),
            new Sprite(64, 1, 6, SpriteSheet.hrTiles),
            new Sprite(64, 2, 6, SpriteSheet.hrTiles),
            new Sprite(64, 3, 6, SpriteSheet.hrTiles),
            new Sprite(64, 4, 6, SpriteSheet.hrTiles)
    };

    public Sprite(int size, int x, int y, SpriteSheet spriteSheet){
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        this.x = x * size;
        this.y = y * size;
        this.sheet = spriteSheet;
        load();
    }

    public Sprite(int size, int color) {
        SIZE = size;
        pixels = new int[SIZE*SIZE];
        setColour(color);
    }

    public Sprite(int[] pixels, int width, int height) {
        SIZE = (width == height) ? width : -1;
        this.pixels = new int[pixels.length];
        for(int i = 0; i < pixels.length; i ++) {
            this.pixels[i] = pixels[i];
        }
    }

    public static Sprite rotate(Sprite sprite, double angle) {
        return new Sprite(rotate(sprite.pixels, sprite.SIZE, sprite.SIZE, angle), sprite.SIZE, sprite.SIZE);
    }

    private static int[] rotate(int[] pixels, int width, int height, double angle) {
        int[] result = new int[width * height];

        double nx_x = rotX(-angle, 1.0, 0.0);
        double nx_y = rotY(-angle, 1.0, 0.0);
        double ny_x = rotX(-angle, 0.0, 1.0);
        double ny_y = rotY(-angle, 0.0, 1.0);

        double x0 = rotX(-angle, -width/2.0, -height/2.0) + width/2.0;
        double y0 = rotY(-angle, -width/2.0, -height/2.0) + height/2.0;

        for(int y = 0; y < height; y ++) {
            double x1 = x0;
            double y1 = y0;
            for(int x = 0; x < width; x ++) {
                int xx = (int) x1;
                int yy = (int) y1;
                int col;
                if(xx < 0 || xx >= width || yy < 0 || yy >= height)
                    col = 0xFFFF00FF;
                else
                    col = pixels[xx + yy * width];
                result[x + y * width] = col;
                x1 += nx_x;
                y1 += nx_y;
            }
            x0 += ny_x;
            y0 += ny_y;
        }

        return result;
    }

    private static double rotX(double angle, double x, double y) {
        double cos = Math.cos(angle - Math.PI/2);
        double sin = Math.sin(angle - Math.PI/2);
        return x * cos + y * -sin;
    }

    private static double rotY(double angle, double x, double y) {
        double cos = Math.cos(angle - Math.PI/2);
        double sin = Math.sin(angle - Math.PI/2);
        return x * sin + y * cos;
    }

    private void setColour(int color) {
        for(int i = 0; i < SIZE * SIZE; i ++) {
            pixels[i] = color;
        }
    }
    private void load() {
        for(int y = 0; y < SIZE; y ++)
            for(int x = 0; x < SIZE; x ++) {
                pixels [x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
            }
    }

}

package com.asteroids.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {

    private String path;
    public final int SIZE;
    public int[] pixels;


    public SpriteSheet(String path, int size) {
        this.path = path;
        this.SIZE = size;
        pixels = new int[SIZE * SIZE];
        load();
    }

    public static SpriteSheet tiles = new SpriteSheet("/res/spritesheet.png", 256);
    public static SpriteSheet colliders = new SpriteSheet("/res/collisionsheet.png", 256);

    public static SpriteSheet hrTiles = new SpriteSheet("/res/highres-spritesheet.png", 1024);

    private void load() {
        try {
            System.out.println(path);
            BufferedImage image = ImageIO.read(getClass().getResource(path));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

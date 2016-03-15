package com.asteroids.level;

import com.asteroids.entity.Entity;
import com.asteroids.entity.mob.PowerUp;
import com.asteroids.entity.projectile.BulletProjectile;
import com.asteroids.entity.spawner.ParticleSpawner;
import com.asteroids.entity.mob.Asteroid;
import com.asteroids.entity.particle.Particle;
import com.asteroids.entity.projectile.Projectile;
import com.asteroids.entity.spawner.Spawner;
import com.asteroids.graphics.Animation;
import com.asteroids.graphics.Screen;
import com.asteroids.graphics.Sprite;
import com.asteroids.math.Vector2d;
import com.asteroids.math.Vector2i;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Level {

    protected int width, height;
    protected int[] tiles;
    public int score = 0;
    public boolean ended = false;
    public List<Asteroid> asteroids = new ArrayList<>();
    public List<Projectile> projectiles = new ArrayList<>();
    public List<Entity> entities = new ArrayList<>();
    public List<Particle> particles = new ArrayList<>();
    public int maxScore = 0;
    public static boolean isDebugging = false;

    public Level(int width, int height)
    {
        this.width = width;
        this.height = height;
        tiles = new int[width * height];
        generateLevel();

    }

    public void restartLevel() {
        asteroids.clear();
        projectiles.clear();
        entities.clear();
        particles.clear();
        score = 0;

    }

    public void add(Entity e) {
        if(e instanceof Particle){
            particles.add((Particle)e);
        }
        else if(e instanceof Projectile) {
            projectiles.add((Projectile) e);
        }
        else if(e instanceof Asteroid) {
            asteroids.add((Asteroid) e);
        }
        else entities.add(e);
    }

    protected void generateLevel() {

    }


    public void update() {

        for(int i = 0; i < asteroids.size(); i ++)
            asteroids.get(i).update();
        for(int i = 0; i < projectiles.size(); i ++)
            projectiles.get(i).update();
        for(int i = 0; i < entities.size(); i ++)
            entities.get(i).update();
        for(int i = 0; i < particles.size(); i ++)
            particles.get(i).update();

      checkCollisions();

        remove();
    }

    private void checkCollisions() {
        if(!isDebugging)
            for(Projectile p : projectiles) {
                for(Asteroid a : asteroids) {
                    if(Vector2d.distance(p.getPos(), a.getPos()) <= 50) {
                        p.collisionMask.generateCollisionPoints();
                        a.collisionMask.generateCollisionPoints();
                        boolean collision = false;
                        for(Vector2i posA : p.collisionMask.collisionPoints) {
                            for(Vector2i posB : a.collisionMask.collisionPoints) {
                                if(new Vector2i(-posB.getX() + (int)p.pos.getX(), -posB.getY() + (int)p.pos.getY()).equals(new Vector2i(posA.getX() + (int)a.pos.getX() - a.collisionMask.getCollisionSprite().SIZE/2, posA.getY() + (int)a.pos.getY() - a.collisionMask.getCollisionSprite().SIZE/2)))  {
                                    a.health -= BulletProjectile.getDamage();
                                    if(a.health <= 0)
                                    {
                                        score ++;
                                        a.remove();
                                        Animation explosion = new Animation(a.getPos(), (float) 0.3, Sprite.explosion, false);
                                        add(explosion);
                                        dropPowerUp(a.getPos());

                                    }
                                    collision = true;
                                    ParticleSpawner s;
                                    if(BulletProjectile.isPowerUp()) {
                                        s = new ParticleSpawner(posA.toVector2d().sum(p.pos), 100, 50, Spawner.Type.PARTICLE_PU);
                                        add(s);
                                    }
                                    else {
                                        s = new ParticleSpawner(posA.toVector2d().sum(p.pos), 44, 15, Spawner.Type.PARTICLE);
                                        add(s);
                                    }
                                    p.remove();
                                    break;
                                }
                            }
                            if(collision) break;
                        }
                    }
                }
            }
    }

    private void dropPowerUp(Vector2d pos) {
        double d = Math.random();
        PowerUp up;
        if(d < 0.2)
        {
            up = new PowerUp(pos, PowerUp.Type.POWER);
            add(up);
            add(up.animation);
        }
        else if(d < 0.4)
        {
            up = new PowerUp(pos, PowerUp.Type.SPEED);
            add(up);
            add(up.animation);
        }

    }
    private void remove() {
        for(int i = 0; i < asteroids.size(); i ++)
            if(asteroids.get(i).isRemoved())asteroids.remove(i);
        for(int i = 0; i < projectiles.size(); i ++)
            if(projectiles.get(i).isRemoved())projectiles.remove(i);
        for(int i = 0; i < entities.size(); i ++)
            if(entities.get(i).isRemoved()) entities.remove(i);
        for(int i = 0; i < particles.size(); i ++)
            if(particles.get(i).isRemoved())particles.remove(i);
    }

    public boolean checkSphereCollision(Asteroid a, Projectile b) {
        return Vector2d.distance(a.getPos(), b.getPos()) < a.radius + b.radius;
    }

    public void endLevel() {
        ended = true;

        String fileName = "game.save";
        String line;

        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                String[] words = line.split(" ");
                maxScore = Integer.parseInt(words[0]);
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        if(score > maxScore) {
            List<String> lines = Arrays.asList(Integer.toString(score));
            Path file = Paths.get("game.save");
            try {
                Files.write(file, lines, Charset.forName("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void render(int xScroll, int yScroll, Screen screen) {
        screen.setOffset(xScroll, yScroll);

        int x0 = xScroll >> 4;
        int x1 = (xScroll + screen.getWidth() + 16) >> 4;
        int y0 = yScroll >> 4;
        int y1 = (yScroll + screen.getHeight() + 16) >> 4;

        for(int y = y0; y < y1; y ++) {
            for(int x = x0; x < x1; x ++) {
                getTile(x, y).render(x, y, screen);
            }
        }

        for(int i = 0; i < asteroids.size(); i ++)
            asteroids.get(i).render(screen);
        for(int i = 0; i < projectiles.size(); i ++)
            projectiles.get(i).render(screen);
        for(int i = 0; i < entities.size(); i ++)
            entities.get(i).render(screen);
        for(int i = 0; i < particles.size(); i ++)
            particles.get(i).render(screen);
    }

    public Tile getTile(int x, int y) {
        if(x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
        switch(tiles[x + y * width])
        {
            case 0:
                return Tile.space;

            case 1:
                return Tile.space2;

            case 2:
                return Tile.space3;
        }

        return Tile.voidTile;
    }

}

package io.github.some_example_name.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

// This is for the colour don't get confused with the other one cuz intellij just imports some bs
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;

import java.util.List;


public class Goose {
    float x, y;
    float speed;
    int size;

    float distance, dx, dy;

    Color colour = new Color(Color.WHITE);

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSize() {
        return size;
    }


    public Goose(float x, float y, float speed, int size) {
        /**
         * Geese chase player and can deal supposed damage to the player
         *
         * @param speed how many pixels the goose covers per tick.
         * @param size is radius, atm the goose is just a circle (placeholder)
         */
        this.x = x; this.y = y;
        this.speed = speed;
        this.size = size;
    }

    public void chasePlayer(Player player) {
        if (distance > speed) {
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        }
    }

    public void separate(List<Goose> geese, Player player) {
        float ox, oy, distance, push;

        if (checkCollision(player)) {
            ox = x - player.getX(); oy = y - player.getY();
            distance = Math.max((float) Math.sqrt(ox*ox + oy*oy), 0.01F);
            push = (player.getSize() + size - distance) / 2f;

            x += ox / distance * push; y += oy / distance * push;
        }

        // This is for the geese
        for (Goose goose : geese) {
            if (checkCollision(goose)) {
                ox = x - goose.getX(); oy = y - goose.getY();
                distance = Math.max((float) Math.sqrt(ox*ox + oy*oy), 0.01F);
                /*
                You may or may not need to adjust this push line, it's multiplying by 2 because
                all geese are the same size anyways
                 */
                push = (2 * size - distance) / 2f;

                x += ox / distance * push; y += oy / distance * push;
            }
        }
    }

    public boolean checkCollision(Player player) {
        Circle playerCircle = new Circle(player.getX(), player.getY(), player.getSize());
        Circle thisGoose = new Circle(x, y, size);
        return playerCircle.overlaps(thisGoose);
    }

    public boolean checkCollision(Goose goose) {
        if (this == goose) {
            return false;
        }

        Circle gooseCircle = new Circle(goose.getX(), goose.getY(), goose.getSize());
        Circle thisGoose = new Circle(x, y, size);
        return gooseCircle.overlaps(thisGoose);
    }

    public void update(Player player, List<Goose> geese) {
        dx = player.getX() - x; dy = player.getY() - y;
        distance = (float) Math.sqrt(dx*dx + dy*dy);
        chasePlayer(player);
        separate(geese, player);
    }

    public void draw(ShapeRenderer shape) {
        /**
         * @param shape for this class to call any drawing functionality,
         *              we need an instance of a libGDX ShapeRenderer object
         */
        shape.setColor(colour);
        shape.circle(x, y, size);
    }


}
